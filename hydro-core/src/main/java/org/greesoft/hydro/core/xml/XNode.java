package org.greesoft.hydro.core.xml;

import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Properties;

/**
 * XML节点
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class XNode {

	/**
	 * 节点
	 */
	private final Node node;

	/**
	 * 节点名称
	 */
	private final String name;

	/**
	 * 节点内容
	 */
	private final String body;

	/**
	 * 属性
	 */
	private final Properties attributes;

	/**
	 * 解析器
	 */
	private final XPathParser xpathParser;

	public XNode(XPathParser xpathParser, Node node) {
		this.xpathParser = xpathParser;
		this.node = node;
		this.name = node.getNodeName();
		this.attributes = parseAttributes(node);
		this.body = parseBody(node);
	}

	public XNode newXNode(Node node) {
		return new XNode(xpathParser, node);
	}

	/**
	 * 获取属性值
	 *
	 * @param name the 属性名
	 * @return the string 属性值
	 */
	public String getStringAttribute(String name) {
		return getStringAttribute(name, null);
	}

	/**
	 * 获取属性值
	 *
	 * @param name the 属性名
	 * @param def  the 属性默认值
	 * @return the string 属性值
	 */
	public String getStringAttribute(String name, String def) {
		String value = attributes.getProperty(name);
		return value == null ? def : value;
	}

	/**
	 * 解析Node获取List节点
	 *
	 * @param expression the expression
	 * @return the list
	 */
	public List<XNode> evalNodes(String expression) {
		return xpathParser.evalNodes(node, expression);
	}

	public String getBody() {
		return body;
	}

	public Node getNode() {
		return node;
	}

	public String getStringBody(String def) {
		if (body == null) {
			return def;
		} else {
			return body;
		}
	}

	public String getName() {
		return name;
	}

	public Properties getAttributes() {
		return attributes;
	}

	/**
	 * 解析Node获取属性
	 *
	 * @param n Node
	 * @return the properties
	 */
	private Properties parseAttributes(Node n) {
		Properties properties = new Properties();
		NamedNodeMap attributeNodes = n.getAttributes();
		if (attributeNodes != null) {
			for (int i = 0; i < attributeNodes.getLength(); i++) {
				Node attribute = attributeNodes.item(i);
				properties.put(attribute.getNodeName(), attribute.getNodeValue());
			}
		}

		return properties;
	}

	/**
	 * 解析Node获取Body
	 *
	 * @param node Node
	 * @return the string
	 */
	private String parseBody(Node node) {
		String data = getBodyData(node);
		if (data == null) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				data = getBodyData(child);
				if (data != null) {
					break;
				}
			}
		}

		return data;
	}

	/**
	 * 根据Node获取body内容
	 *
	 * @param child Node
	 * @return the body data
	 */
	private String getBodyData(Node child) {
		if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE) {
			return ((CharacterData) child).getData();
		}
		return null;
	}
}
