package org.greesoft.hydro.core.xml;

import org.greesoft.hydro.core.exception.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * XML解析器
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class XPathParser {

	/**
	 * ROOT
	 */
	private Document document;

	/**
	 * XPATH
	 */
	private XPath xpath;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(XPathParser.class);

	public XPathParser(InputStream inputStream) {
		XPathFactory factory = XPathFactory.newInstance();
		this.xpath = factory.newXPath();
		this.document = createDocument(new InputSource(inputStream));
	}

	/**
	 * 根据expression获取节点
	 *
	 * @param expression the XPATH
	 * @return the node
	 */
	public XNode getNode(String expression) {

		Node node = (Node) evaluate(expression, document, XPathConstants.NODE);

		if (null == node) {
			return null;
		}

		return new XNode(this, node);
	}

	/**
	 * 获取节点列表
	 *
	 * @param root       the ROOT节点
	 * @param expression the XPATH
	 * @return the list
	 */
	public List<XNode> evalNodes(Object root, String expression) {
		List<XNode> xnodes = new ArrayList<>();
		NodeList nodes = (NodeList) evaluate(expression, root, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			xnodes.add(new XNode(this, nodes.item(i)));
		}
		return xnodes;
	}

	/**
	 * XPath解析xml
	 *
	 * @param expression the XPATH路径
	 * @param root       the document
	 * @param returnType the QName
	 * @return the object
	 */
	private Object evaluate(String expression, Object root, QName returnType) {
		try {
			return xpath.evaluate(expression, root, returnType);
		} catch (Exception e) {
			throw new BuilderException("Error evaluating XPath.  Cause: " + e, e);
		}
	}

	/**
	 * 根据Xml创建Document，并进行dtd验证
	 *
	 * @param inputSource the input source
	 * @return the document
	 */
	private Document createDocument(InputSource inputSource) {
		try {

			logger.debug("Create Xml Document And Validate DTD!");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			// 打开验证
			factory.setValidating(true);

			// 设置Namespace有效
			factory.setNamespaceAware(false);

			//忽略注释
			factory.setIgnoringComments(true);

			//忽略空白
			factory.setIgnoringElementContentWhitespace(false);

			//把 CDATA 节点转换为 Text 节点
			factory.setCoalescing(false);

			//扩展实体引用
			factory.setExpandEntityReferences(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			// 强制设置DTD的位置为resource
			builder.setEntityResolver((String publicId, String systemId) -> {
				ClassPathResource classPathResource = new ClassPathResource(systemId.substring(1), XPathParser.class.getClassLoader());
				InputStream inputStream = classPathResource.getInputStream();
				return new InputSource(inputStream);
			});

			// 解析XSD错误
			builder.setErrorHandler(new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					// warning
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
					throw exception;
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}
			});
			return builder.parse(inputSource);
		} catch (Exception e) {
			throw new BuilderException("Error creating document instance.  Cause: " + e, e);
		}
	}
}
