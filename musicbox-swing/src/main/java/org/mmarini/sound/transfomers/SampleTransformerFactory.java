/*
 * SampleTransformerFactory.java
 *
 * $Id: SampleTransformerFactory.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SampleTransformerFactory.java,v 1.1 2008/11/15 08:49:48 marco
 *          Exp $
 * 
 */
public interface SampleTransformerFactory {

	/**
	 * @param frequence
	 * @param threshold
	 * @param attackTime
	 * @param decayTime
	 * @return
	 */
	public abstract AutoMuter createAutoMuter(float frequence, float threshold,
			float attackTime, float decayTime);

	/**
	 * @param delay
	 * @param echoLevel
	 * @param signalLevel
	 * @return
	 */
	public abstract Echo createEcho(float delay, float echoLevel,
			float signalLevel);

	/**
	 * @param frequence
	 * @param peakDelay
	 * @return
	 */
	public abstract DecibelLevelMeter createLevelMeter(float frequence,
			float peakDelay);

	/**
	 * @param transformers
	 * @return
	 */
	public abstract Mixer createMixer(SampleTransformer[] transformers);

	/**
	 * @param frequence
	 * @param amplitude
	 * @param offset
	 * @return
	 */
	public abstract Modulator createMoulator(float frequence, float amplitude,
			float offset);

	/**
	 * @param transformers
	 * @return
	 */
	public abstract Sequencer createSequencer(SampleTransformer[] transformers);

	/**
	 * @param frequence
	 * @param toneShift
	 * @return
	 */
	public abstract ToneShifter createToneShifter(float frequence,
			float toneShift);

}
