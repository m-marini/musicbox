/*
 * EchoNode.java
 *
 * $Id: EchoNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
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
 * @version $Id: EchoNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class EchoNode extends AbstractTransformNode {

	private float delay;

	private float signalLevel;

	private float echoLevel;

	/**
	 * @param soundProcessor
	 */
	protected EchoNode(SoundProcessor soundProcessor) {
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
		SampleTransformer transformer = getSoundProcessor().createEcho(
				getDelay(), getEchoLevel(), getSignalLevel());
		setSampleTransformer(transformer);
		return transformer;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		addAttribute(element, "echoLevel", getEchoLevel());
		addAttribute(element, "signalLevel", getSignalLevel());
		addAttribute(element, "delay", getDelay());
		return element;
	}

	/**
	 * @return the delay
	 */
	public float getDelay() {
		return delay;
	}

	/**
	 * @return the echoLevel
	 */
	public float getEchoLevel() {
		return echoLevel;
	}

	/**
	 * @return the signalLevel
	 */
	public float getSignalLevel() {
		return signalLevel;
	}

	/**
	 * @param delay
	 *            the delay to set
	 */
	public void setDelay(float delay) {
		this.delay = delay;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param echoLevel
	 *            the echoLevel to set
	 */
	public void setEchoLevel(float echoLevel) {
		this.echoLevel = echoLevel;
		getSoundProcessor().handleNodeChanged(this);
	}

	/**
	 * @param signalLevel
	 *            the signalLevel to set
	 */
	public void setSignalLevel(float signalLevel) {
		this.signalLevel = signalLevel;
		getSoundProcessor().handleNodeChanged(this);
	}
}
