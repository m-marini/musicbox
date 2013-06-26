/*
 * TransformNode.java
 *
 * $Id: TransformNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.SampleTransformer;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: TransformNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public interface TransformNode {
	/**
	 * 
	 * @param visitor
	 */
	public void apply(TransformNodeVisitor visitor);

	/**
         * 
         */
	public SampleTransformer buildTransformer();

	/**
	 * @param parent
	 */
	public Element createElement(Element parent);

	/**
	 * @return
	 */
	public SampleTransformer getSampleTransformer();
}
