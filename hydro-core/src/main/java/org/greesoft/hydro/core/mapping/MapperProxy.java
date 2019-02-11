package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.InvocationHandler;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 接口映射代理
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class MapperProxy<T> implements InvocationHandler, Serializable {

	private static final long serialVersionUID = -6424540398559729838L;

	/**
	 * DAO接口
	 */
	private final Class<T> mapperInterface;

	/**
	 * 函数缓存
	 */
	private final transient Map<Method, MapperMethod> methodCache;

	/**
	 * SqlSession
	 */
	private final transient SqlSession sqlSession;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MapperProxy.class);

	public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
		this.mapperInterface = mapperInterface;
		this.methodCache = methodCache;
		this.sqlSession = sqlSession;
	}

	/**
	 * 执行代理方法
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 * @param object 对象
	 * @param method 调用方法
	 * @param args 参数
	 * @return java.lang.Object
	 */
	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {

		if (Object.class.equals(method.getDeclaringClass())) {
			try {
				return method.invoke(this, args);
			} catch (Exception e) {
				logger.error("ORM invoke fail!, msg => [{}]", e);
			}
		}
		final MapperMethod mapperMethod = cachedMapperMethod(method);
		return mapperMethod.execute(sqlSession, args);
	}

	/**
	 * 缓存
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param method 参数说明
	 * @return MapperMethod
	 */
	private MapperMethod cachedMapperMethod(Method method) {
		return methodCache.computeIfAbsent(method, k -> new MapperMethod(mapperInterface, k, sqlSession.getConfiguration()));
	}
}
