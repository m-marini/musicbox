/*
 * SampleTransformerFactoryImpl.java
 *
 * $Id: SampleTransformerFactoryImpl.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import javax.sound.sampled.AudioFormat;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SampleTransformerFactoryImpl.java,v 1.1 2008/11/15 08:49:48
 *          marco Exp $
 * 
 */
public class SampleTransformerFactoryImpl implements SampleTransformerFactory {
	private AudioFormat format;

	/**
         * 
         */
	public SampleTransformerFactoryImpl() {
	}

	/**
	 * @param format
	 */
	public SampleTransformerFactoryImpl(AudioFormat format) {
		this.format = format;
	}

	/**
	 * @param cutOffFrequence
	 * @param scale
	 * @return
	 */
	private int convertDecay(float cutOffFrequence, int scale) {
		float k = (float) -Math.expm1(-2. * Math.PI * cutOffFrequence
				/ format.getSampleRate());
		return convertLinearLevel(k, scale);
	}

	/**
	 * @param frequence
	 * @return
	 */
	public int convertFrequence(float frequence) {
		return convertTime(1f / frequence);
	}

	/**
	 * 
	 * @param amplitude
	 * @param scale
	 * @return
	 */
	public int convertLinearLevel(float amplitude, int scale) {
		return Math.round(scale * amplitude);
	}

	/**
	 * @param level
	 * @param scale
	 * @return
	 */
	public int convertLogaritmicLevel(float level, int scale) {
		return convertLinearLevel((float) Math.pow(10., level / 20.), scale);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public int convertTime(float time) {
		return Math.round(getFormat().getSampleRate() * time);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerFactory#createAutoMuter(float,
	 *      float, float, float)
	 */
	@Override
	public AutoMuter createAutoMuter(float frequence, float threshold,
			float attackTime, float decayTime) {
		float time = Math.max(attackTime, decayTime);
		int rampSize = convertTime(time);
		int scale = convertDecay(frequence, LevelMeter.SCALE);
		int attackDelta;
		int decayDelta;
		if (attackTime > decayTime) {
			attackDelta = AutoMuter.TIME_SCALE;
			decayDelta = convertLinearLevel(attackTime / decayTime,
					AutoMuter.TIME_SCALE);
		} else {
			decayDelta = AutoMuter.TIME_SCALE;
			attackDelta = convertLinearLevel(decayTime / attackTime,
					AutoMuter.TIME_SCALE);
		}
		int thresholdInt = convertLogaritmicLevel(threshold, getMaxLevel());
		return new AutoMuter(scale, thresholdInt, rampSize, attackDelta,
				decayDelta);
	}

	/**
         * 
         */
	@Override
	public Echo createEcho(float delay, float echoLevel, float signalLevel) {
		int signalLevelInt = convertLogaritmicLevel(signalLevel, Echo.LEVEL);
		int echoLevelInt = convertLogaritmicLevel(echoLevel, Echo.LEVEL);
		int size = convertTime(delay);
		return new Echo(size, signalLevelInt, echoLevelInt);
	}

	/**
	 * @param cutOffFrequence
	 * @param persistence
	 * @return
	 */
	@Override
	public DecibelLevelMeter createLevelMeter(float frequence, float peakDelay) {
		int scale = convertDecay(frequence, LevelMeter.SCALE);
		int time = convertTime(peakDelay);
		return new DecibelLevelMeter(scale, time, getMaxLevel());
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerFactory#createMixer(org.mmarini.sound.transfomers.SampleTransformer[])
	 */
	@Override
	public Mixer createMixer(SampleTransformer[] transformers) {
		return new Mixer(transformers);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerFactory#createMoulator(float,
	 *      float, float)
	 */
	@Override
	public Modulator createMoulator(float frequence, float amplitude,
			float offset) {
		int size = convertFrequence(frequence);
		int amp = convertLinearLevel(amplitude, Modulator.LEVEL);
		int off = convertLinearLevel(offset, Modulator.LEVEL);
		return new Modulator(size, amp, off);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerFactory#createSequencer(org.mmarini.sound.transfomers.SampleTransformer[])
	 */
	@Override
	public Sequencer createSequencer(SampleTransformer[] transformers) {
		return new Sequencer(transformers);
	}

	/**
         * 
         */
	@Override
	public ToneShifter createToneShifter(float frequence, float toneShift) {
		float speed = (float) Math.pow(2, toneShift / 12.);
		int size = convertFrequence(frequence);
		int scale = convertLinearLevel(speed, ToneShifter.TIME_SCALE);
		return new ToneShifter(size, scale);
	}

	/**
	 * @return the format
	 */
	public AudioFormat getFormat() {
		return format;
	}

	/**
	 * @return
	 */
	private int getMaxLevel() {
		return 1 << (format.getSampleSizeInBits() - 1);
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(AudioFormat format) {
		this.format = format;
	}
}