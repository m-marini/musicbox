/*
 * DOMBuilder.java
 *
 * $Id: DOMBuilder.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 08/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.xml;

import javax.xml.XMLConstants;

import org.mmarini.sound.model.TransformNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: DOMBuilder.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class DOMBuilder implements NodeXMLConstants {
	private static DOMBuilder instance = new DOMBuilder();

	/**
	 * @return the instance
	 */
	public static DOMBuilder getInstance() {
		return instance;
	}

	/**
         * 
         */
	protected DOMBuilder() {
	}

	/**
	 * 
	 * @param document
	 * @param transformNode
	 */
	public void build(Document document, TransformNode transformNode) {
		Element root = document.createElementNS(NAME_SPACE, BOX_ELEMENT);
		root.setAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI,
				"xsi:schemaLocation", SCHEMA_LOCATION);
		document.appendChild(root);
		transformNode.createElement(root);
		document.normalize();
	}
}
