/*
 * VolumeNode.java
 *
 * $Id: VolumeNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.SampleTransformer;
import org.mmarini.sound.transfomers.VolumeControl;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: VolumeNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class VolumeNode extends AbstractTransformNode {

	private float gain;

	/**
	 * @param soundProcessor
	 */
	protected VolumeNode(SoundProcessor soundProcessor) {
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
		int scale = (int) Math.round(Math.pow(10, getGain() / 20.)
				* VolumeControl.SCALE);
		VolumeControl transformer = new VolumeControl(scale);
		setSampleTransformer(transformer);
		return transformer;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		addAttribute(element, "gain", getGain());
		return element;
	}

	/**
	 * @return the gain
	 */
	public float getGain() {
		return gain;
	}

	/**
	 * @param gain
	 *            the gain to set
	 */
	public void setGain(float gain) {
		this.gain = gain;
		getSoundProcessor().handleNodeChanged(this);
	}
}
