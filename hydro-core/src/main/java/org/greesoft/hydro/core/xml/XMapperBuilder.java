package org.greesoft.hydro.core.xml;

import org.greesoft.hydro.core.exception.BuilderException;
import org.greesoft.hydro.core.io.Resources;
import org.greesoft.hydro.core.mapping.MapperBuilderAssistant;
import org.greesoft.hydro.core.session.SqlSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

/**
 * XML映射构建器
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class XMapperBuilder {

	/**
	 * SqlSession配置
	 */
	private SqlSessionConfig configuration;

	/**
	 * 资源
	 */
	private String resource;

	/**
	 * XML解析器
	 */
	private XPathParser parser;

	/**
	 * 映射构建器助手
	 */
	private MapperBuilderAssistant builderAssistant;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(XMapperBuilder.class);

	public XMapperBuilder(InputStream inputStream, String resource, SqlSessionConfig configuration) {
		this(new XPathParser(inputStream), configuration, resource);
	}

	/**
	 * 解析
	 */
	public void parse() {
		if (configuration.isResourceLoaded(resource)) {
			return;
		}

		// 配置节点
		configurationElement(parser.getNode("/mapper"));
		// 标记一下，已经加载过了
		configuration.addLoadedResource(resource);
		// 绑定映射器到namespace
		bindMapperForNamespace();
	}

	/**
	 * 配置节点
	 *
	 * @param context the context
	 */
		private void configurationElement(XNode context) {
		try {
			String namespace = context.getStringAttribute("namespace");
			if (namespace == null || namespace.equals("")) {
				throw new BuilderException("Mapper's namespace cannot be empty");
			}

			builderAssistant.setCurrentNamespace(namespace);
			// 配置sql
			buildStatementFromContext(context.evalNodes("sql"));
		} catch (Exception e) {
			throw new BuilderException("Error parsing Mapper XML. Cause: " + e, e);
		}
	}

	/**
	 * 构建语句
	 *
	 * @param list the list
	 */
	private void buildStatementFromContext(List<XNode> list) {
		for (XNode context : list) {
			//构建所有语句,一个mapper下可以有很多sql
			//语句比较复杂，核心都在这里面，所以调用XMLStatementBuilder
			final XStatementBuilder statementParser = new XStatementBuilder(builderAssistant, context);
			statementParser.parseStatementNode();
		}
	}

	/**
	 * 绑定命名空间到对应接口中
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @return void
	 */
	private void bindMapperForNamespace() {

		String namespace = builderAssistant.getCurrentNamespace();

		if (null == namespace) {
			return;
		}

		Class<?> boundType = null;
		try {
			boundType = Resources.classForName(namespace);
		} catch (ClassNotFoundException e) {
			logger.error("bindMapperForNamespace namespace [{}] interface not found!", namespace);
		}

		if (null == boundType || configuration.hasMapper(boundType)) {
			return;
		}

		configuration.addLoadedResource("namespace:" + namespace);
		configuration.addMapper(boundType);

		logger.debug("bindMapperForNamespace namespace => [{}] bind mapper success!", namespace);
	}

	private XMapperBuilder(XPathParser parser, SqlSessionConfig configuration, String resource) {
		this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
		this.configuration = configuration;
		this.parser = parser;
		this.resource = resource;
	}
}
