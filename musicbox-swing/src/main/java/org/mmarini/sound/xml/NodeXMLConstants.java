/*
 * NodeXMLConstants.java
 *
 * $Id: NodeXMLConstants.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 09/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.xml;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: NodeXMLConstants.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public interface NodeXMLConstants {

	/**
         * 
         */
	public static final String CLASS_ATTR = "class";

	/**
         * 
         */
	public static final String NAME_ATTR = "name";

	/**
         * 
         */
	public static final String ATTRIBUTE_ELEMENT = "attribute";

	/**
         * 
         */
	public static final String TRANSFORMER_ELEMENT = "transformer";

	/**
         * 
         */
	public static final String BOX_ELEMENT = "box";

	/**
         * 
         */
	public static final String SCHEMA_NAME = "box-0.1.0";

	/**
         * 
         */
	public static final String NAME_SPACE = "http://www.mmarini.org/"
			+ SCHEMA_NAME;

	/**
         * 
         */
	public static final String SCHEMA_LOCATION = NAME_SPACE + " " + SCHEMA_NAME
			+ ".xsd";

}
