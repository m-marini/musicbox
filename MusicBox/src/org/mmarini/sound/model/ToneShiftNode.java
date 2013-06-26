/*
 * ToneShiftNode.java
 *
 * $Id: ToneShiftNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
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
 * @version $Id: ToneShiftNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class ToneShiftNode extends AbstractTransformNode {

	private float frequence;

	private float toneShift;

	/**
	 * @param soundProcessor
	 */
	protected ToneShiftNode(SoundProcessor soundProcessor) {
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
		SampleTransformer transformer = getSoundProcessor().createToneShifter(
				getFrequence(), getToneShift());
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
		addAttribute(element, "toneShift", getToneShift());
		return element;
	}

	/**
	 * @return the frequence
	 */
	public float getFrequence() {
		return frequence;
	}

	/**
	 * @return the toneShift
	 */
	public float getToneShift() {
		return toneShift;
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
	 * @param toneShift
	 *            the toneShift to set
	 */
	public void setToneShift(float toneShift) {
		this.toneShift = toneShift;
		getSoundProcessor().handleNodeChanged(this);
	}
}
