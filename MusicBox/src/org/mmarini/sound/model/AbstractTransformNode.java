/*
 * AbstractTransformNode.java
 *
 * $Id: AbstractTransformNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.SampleTransformer;
import org.mmarini.sound.xml.NodeXMLConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractTransformNode.java,v 1.1 2008/11/15 08:49:49 marco Exp
 *          $
 * 
 */
public abstract class AbstractTransformNode implements TransformNode,
		NodeXMLConstants {

	private SoundProcessor soundProcessor;

	private SampleTransformer sampleTransformer;

	/**
         * 
         */
	protected AbstractTransformNode(SoundProcessor soundProcessor) {
		this.soundProcessor = soundProcessor;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	protected void addAttribute(Element element, String name, float value) {
		Document doc = element.getOwnerDocument();
		Element attr = doc.createElementNS(NAME_SPACE, ATTRIBUTE_ELEMENT);
		attr.setAttribute(NAME_ATTR, name);
		attr.appendChild(doc.createTextNode(String.valueOf(value)));
		element.appendChild(attr);
	}

	/**
	 * @param parent
	 * @return
	 */
	@Override
	public Element createElement(Element parent) {
		Document doc = parent.getOwnerDocument();
		Element element = doc.createElementNS(NAME_SPACE, TRANSFORMER_ELEMENT);
		element.setAttribute(CLASS_ATTR, getClass().getName());
		parent.appendChild(element);
		return element;
	}

	/**
	 * @return the sampleTransformer
	 */
	@Override
	public SampleTransformer getSampleTransformer() {
		return sampleTransformer;
	}

	/**
	 * @return the soundProcessor
	 */
	public SoundProcessor getSoundProcessor() {
		return soundProcessor;
	}

	/**
	 * @param sampleTransformer
	 *            the sampleTransformer to set
	 */
	protected void setSampleTransformer(SampleTransformer sampleTransformer) {
		this.sampleTransformer = sampleTransformer;
	}

}