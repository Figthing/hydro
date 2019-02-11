package org.greesoft.hydro.core.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML脚本构建器
 * <p> Date             :2017/12/21 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class XScriptBuilder {

	private XNode context;

	public XScriptBuilder(XNode context) {
		this.context = context;
	}

	/**
	 * 解析脚本
	 *
	 * @return the string
	 */
	public String parseScriptNode() {
		return this.parseDynamicTags(context);
	}

	private String parseDynamicTags(XNode node) {

		StringBuilder data = new StringBuilder();

		NodeList children = node.getNode().getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			XNode child = node.newXNode(children.item(i));

			if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
				data.append(child.getStringBody(""));
			}
		}

		return data.toString();
	}
}
