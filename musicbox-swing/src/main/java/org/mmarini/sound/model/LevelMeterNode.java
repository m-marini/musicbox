/*
 * LevelMeterNode.java
 *
 * $Id: LevelMeterNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.DecibelLevelMeter;
import org.mmarini.sound.transfomers.SampleTransformer;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LevelMeterNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class LevelMeterNode extends AbstractTransformNode {
	private float frequence;

	private float peakDelay;

	/**
	 * @param soundProcessor
	 */
	protected LevelMeterNode(SoundProcessor soundProcessor) {
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
		SampleTransformer transformer = getSoundProcessor().createLevelMeter(
				getFrequence(), getPeakDelay());
		setSampleTransformer(transformer);
		return transformer;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		addAttribute(element, "peakDelay", getPeakDelay());
		addAttribute(element, "frequence", getFrequence());
		return element;
	}

	/**
	 * @return the frequence
	 */
	public float getFrequence() {
		return frequence;
	}

	/**
	 * @return
	 */
	private DecibelLevelMeter getLevelMeter() {
		return (DecibelLevelMeter) getSampleTransformer();
	}

	/**
	 * @return the peakDelay
	 */
	public float getPeakDelay() {
		return peakDelay;
	}

	/**
	 * @return
	 */
	public void retrieveDecibelLevel(float[] values) {
		getLevelMeter().retrieveDecibelLevel(values);
	}

	/**
	 * @return
	 */
	public void retrieveDecibelPeak(float[] values) {
		getLevelMeter().retrieveDecibelPeak(values);
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
	 * @param peakDelay
	 *            the peakDelay to set
	 */
	public void setPeakDelay(float peakDelay) {
		this.peakDelay = peakDelay;
		getSoundProcessor().handleNodeChanged(this);
	}
}
