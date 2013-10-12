/*
 * AutoMuteNode.java
 *
 * $Id: AutoMuteNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
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
 * @version $Id: AutoMuteNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class AutoMuteNode extends AbstractTransformNode {

	private float threshold;

	private float attackTime;

	private float decayTime;

	private float frequence;

	/**
	 * @param soundProcessor
	 */
	protected AutoMuteNode(SoundProcessor soundProcessor) {
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
		SampleTransformer transformer = getSoundProcessor()
				.createAutoMuter(getFrequence(), getThreshold(),
						getAttackTime(), getDecayTime());
		setSampleTransformer(transformer);
		return transformer;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		addAttribute(element, "attackTime", getAttackTime());
		addAttribute(element, "frequence", getFrequence());
		addAttribute(element, "threshold", getThreshold());
		addAttribute(element, "decayTime", getDecayTime());
		return element;
	}

	/**
	 * @return the attackTime
	 */
	public float getAttackTime() {
		return attackTime;
	}

	/**
	 * @return the decayTime
	 */
	public float getDecayTime() {
		return decayTime;
	}

	/**
	 * @return the frequence
	 */
	public float getFrequence() {
		return frequence;
	}

	/**
	 * @return the threshold
	 */
	public float getThreshold() {
		return threshold;
	}

	/**
	 * @param attackTime
	 *            the attackTime to set
	 */
	public void setAttackTime(float attackTime) {
		this.attackTime = attackTime;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param decayTime
	 *            the decayTime to set
	 */
	public void setDecayTime(float decayTime) {
		this.decayTime = decayTime;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param frequence
	 *            the frequence to set
	 */
	public void setFrequence(float cutOffFrequence) {
		this.frequence = cutOffFrequence;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param threshold
	 *            the threshold to set
	 */
	public void setThreshold(float threshold) {
		this.threshold = threshold;
		getSoundProcessor().handleNodeChanged(this);
	}
}