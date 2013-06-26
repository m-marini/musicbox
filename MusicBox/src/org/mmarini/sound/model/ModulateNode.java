/*
 * ModulateNode.java
 *
 * $Id: ModulateNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
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
 * @version $Id: ModulateNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class ModulateNode extends AbstractTransformNode {

	private float frequence;

	private float amplitude;

	private float offset;

	/**
	 * @param soundProcessor
	 */
	protected ModulateNode(SoundProcessor soundProcessor) {
		super(soundProcessor);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNode#apply(org.mmarini.sound.model.TransformNodeVisitor)
	 */
	@Override
	public void apply(TransformNodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNode#buildTransformer()
	 */
	@Override
	public SampleTransformer buildTransformer() {
		SampleTransformer transformer = getSoundProcessor().createMoulator(
				getFrequence(), getAmplitude(), getOffset());
		setSampleTransformer(transformer);
		return transformer;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		addAttribute(element, "frequence", getFrequence());
		addAttribute(element, "amplitude", getAmplitude());
		addAttribute(element, "offset", getOffset());
		return element;
	}

	/**
	 * @return the amplitude
	 */
	public float getAmplitude() {
		return amplitude;
	}

	/**
	 * @return the frequence
	 */
	public float getFrequence() {
		return frequence;
	}

	/**
	 * @return the offset
	 */
	public float getOffset() {
		return offset;
	}

	/**
	 * @param amplitude
	 *            the amplitude to set
	 */
	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param frequence
	 *            the frequence to set
	 */
	public void setFrequence(float frequence) {
		this.frequence = frequence;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(float offset) {
		this.offset = offset;
		getSoundProcessor().handleNodeChanged(this);
	}
}
