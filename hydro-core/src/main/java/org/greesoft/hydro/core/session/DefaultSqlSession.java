package org.greesoft.hydro.core.session;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.greesoft.hydro.core.annotation.Mapping;
import org.greesoft.hydro.core.exception.SqlSessionException;
import org.greesoft.hydro.core.mapping.MapperBeanType;
import org.greesoft.hydro.core.velocity.EscapeJsonReference;
import org.greesoft.hydro.core.velocity.JsonDirective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 默认SqlSession
 * <p> Date             :2017/12/14 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class DefaultSqlSession implements SqlSession {

	/**
	 * SqlSession配置
	 */
	protected SqlSessionConfig configuration;

	/**
	 * 模板
	 */
	protected VelocityEngine velocityEngine;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DefaultSqlSession.class);

	public DefaultSqlSession(SqlSessionConfig configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlSessionConfig getConfiguration() {
		return this.configuration;
	}

	@Override
	public Object executorSql(String statement, Map<String, Object> params) {
		return null;
	}

	@Override
	public <T> T transMap2Bean(Map map, Class<T> clazz) {

		if (null == map || map.isEmpty()) {
			return null;
		}

		List<Field> list = new ArrayList<>();

		// 获取所有的Field
		getClassAllField(clazz, list);

		T result;
		Map datas = new CaseInsensitiveMap(map);

		try {
			result = clazz.newInstance();

			for (Field field: list) {
				field.setAccessible(true);
				String key = field.getName();
				Annotation[] annotations = field.getAnnotations();

				for(Annotation annotation: annotations) {
					if (annotation instanceof Mapping) {
						key = ((Mapping) annotation).value();
						break;
					}
				}

				if (!datas.containsKey(key)) {
					continue;
				}

				Object value = datas.get(key);

				if (null == value) {
					continue;
				}

				// 映射Bean的类型
				MapperBeanType fieldType = MapperBeanType.valueOf(field.getType().getSimpleName().toUpperCase());

				// 执行SET方法
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
				Method setMethod = propertyDescriptor.getWriteMethod();
				setMethod.invoke(result, fieldType.toValue(value));
			}

		} catch (Exception e) {
			logger.error("DefaultSqlSession transMap2Bean Exception!", e);
			throw new SqlSessionException(e);
		}

		return result;
	}

	/**
	 * 获取该Class的所有Field
	 *
	 * @param clazz the clazz
	 * @param list  the list
	 */
	public void getClassAllField(Class clazz, List<Field> list) {

		if (null != clazz.getSuperclass()) {
			getClassAllField(clazz.getSuperclass(), list);
		}

		for(Field field: clazz.getDeclaredFields()) {
			list.add(field);
		}
	}

	/**
	 * 使用Velocity解析字符串
	 *
	 * @param str    字符串
	 * @param params 参数
	 * @return the string
	 */
	protected String velocityParser(String str, Map<String, Object> params) {
		VelocityContext context;

		if (null != params && !params.isEmpty()) {
			context = new VelocityContext(params);
		} else {
			context = new VelocityContext();
		}

		// 解析内容
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context, writer, "", str);
		writer.flush();

		return writer.toString();
	}

	/**
	 * 初始化模板
	 *
	 */
	protected void initVelocity() {
		this.initVelocity(new Properties());
	}

	/**
	 * 初始化模板
	 *
	 * @param properties 参数
	 */
	protected void initVelocity(Properties properties) {
		this.setVelocity(properties);
	}

	/**
	 * 设置Velocity
	 *
	 * @param properties 参数
	 */
	private void setVelocity(Properties properties) {
		properties.setProperty("userdirective", JsonDirective.class.getName());

		if (!properties.containsKey("input.encoding")) {
			properties.setProperty("input.encoding", "UTF-8");
		}

		if (!properties.containsKey("output.encoding")) {
			properties.setProperty("output.encoding", "UTF-8");
		}

		// 初始化模板引擎
		properties.setProperty(Velocity.RESOURCE_LOADER, "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		properties.setProperty("eventhandler.referenceinsertion.class", EscapeJsonReference.class.getName());
		properties.setProperty("eventhandler.escape.json.match", "/.*/");

		// 初始化模板
		velocityEngine = new VelocityEngine(properties);
		velocityEngine.init();
	}
}
