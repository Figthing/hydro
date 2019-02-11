package org.greesoft.hydro.core.xml;

import org.greesoft.hydro.core.mapping.MapperBuilderAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Xml节点解析器
 * <p> Date             :2017/12/13 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class XStatementBuilder {

	/**
	 * 节点
	 */
	private final XNode context;

	/**
	 * 映射构建器助手
	 */
	private MapperBuilderAssistant builderAssistant;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(XStatementBuilder.class);

	public XStatementBuilder(MapperBuilderAssistant builderAssistant, XNode context) {
		this.context = context;
		this.builderAssistant = builderAssistant;
	}

	/**
	 * 解析节点
	 */
	public void parseStatementNode() {
		String id = context.getStringAttribute("id");
		String type = context.getStringAttribute("type").toUpperCase();
		String resultType = context.getStringAttribute("resultType");
		String loop = context.getStringAttribute("loop");
		String item = context.getStringAttribute("item");
		item = item == null ? "item": item;
		String route = context.getStringAttribute("route");
		String startRow = context.getStringAttribute("startRow");
		String endRow = context.getStringAttribute("endRow");

		XScriptBuilder scriptBuilder = new XScriptBuilder(context);
		String sql = scriptBuilder.parseScriptNode();

		logger.debug("XML parser success! XNode name => [{}], attributes => [{}]", context.getName(), context.getAttributes());

		builderAssistant.addMappedStatement(
				id,
				type,
				resultType,
				route,
				loop,
				item,
				sql,
				startRow,
				endRow
		);
	}
}
