/*
 * SoundProcessorImpl.java
 *
 * $Id: SoundProcessorImpl.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;

import org.mmarini.sound.transfomers.AutoMuter;
import org.mmarini.sound.transfomers.DecibelLevelMeter;
import org.mmarini.sound.transfomers.Echo;
import org.mmarini.sound.transfomers.LevelMeter;
import org.mmarini.sound.transfomers.Mixer;
import org.mmarini.sound.transfomers.Modulator;
import org.mmarini.sound.transfomers.SampleTransformer;
import org.mmarini.sound.transfomers.Sequencer;
import org.mmarini.sound.transfomers.ToneShifter;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SoundProcessorImpl.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class SoundProcessorImpl implements SoundProcessor {

	private List<SoundListener> listeners;

	private SoundEvent event;

	private TransformNode transformNode;

	private AudioFormat format;

	/**
         * 
         */
	protected SoundProcessorImpl(AudioFormat format) {
		event = new SoundEvent(this);
		this.format = format;
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#addSoundListener(org.mmarini.sound.model.SoundListener)
	 */
	@Override
	public synchronized void addSoundListener(SoundListener listener) {
		List<SoundListener> listeners = this.listeners;
		if (listeners == null) {
			listeners = new ArrayList<SoundListener>(1);
			listeners.add(listener);
			this.listeners = listeners;
		} else if (!listeners.contains(listener)) {
			listeners = new ArrayList<SoundListener>(listeners);
			listeners.add(listener);
			this.listeners = listeners;
		}
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
	private int convertFrequence(float frequence) {
		return convertTime(1f / frequence);
	}

	/**
	 * 
	 * @param amplitude
	 * @param scale
	 * @return
	 */
	private int convertLinearLevel(float amplitude, int scale) {
		return Math.round(scale * amplitude);
	}

	/**
	 * @param level
	 * @param scale
	 * @return
	 */
	private int convertLogaritmicLevel(float level, int scale) {
		return convertLinearLevel((float) Math.pow(10., level / 20.), scale);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	private int convertTime(float time) {
		return Math.round(getFormat().getSampleRate() * time);
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#createAutoMuteNode()
	 */
	@Override
	public AutoMuteNode createAutoMuteNode() {
		AutoMuteNode autoMuteNode = new AutoMuteNode(this);
		autoMuteNode.setFrequence(20f);
		autoMuteNode.setThreshold(-40f);
		autoMuteNode.setAttackTime(0.1f);
		autoMuteNode.setDecayTime(0.5f);
		return autoMuteNode;
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
	 * @see org.mmarini.sound.model.SoundProcessor#createConnectNode()
	 */
	@Override
	public ConnectNode createConnectNode() {
		return new ConnectNode(this);
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
	 * @see org.mmarini.sound.model.SoundProcessor#createEchoNode()
	 */
	@Override
	public EchoNode createEchoNode() {
		EchoNode echoNode = new EchoNode(this);
		echoNode.setEchoLevel(-6f);
		echoNode.setDelay(0.1f);
		return echoNode;
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
	 * @see org.mmarini.sound.model.SoundProcessor#createLevelMeterNode()
	 */
	@Override
	public LevelMeterNode createLevelMeterNode() {
		LevelMeterNode node = new LevelMeterNode(this);
		node.setFrequence(20f);
		node.setPeakDelay(0.5f);
		return node;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerFactory#createMixer(org.mmarini.sound.transfomers.SampleTransformer[])
	 */
	@Override
	public Mixer createMixer(SampleTransformer[] transformers) {
		return new Mixer(transformers);
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#createMixerNode()
	 */
	@Override
	public MixerNode createMixerNode() {
		return new MixerNode(this);
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#createModulateNode()
	 */
	@Override
	public ModulateNode createModulateNode() {
		ModulateNode modulateNode = new ModulateNode(this);
		modulateNode.setAmplitude(1f);
		modulateNode.setFrequence(0.5f);
		modulateNode.setOffset(0.5f);
		return modulateNode;
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
	 * @see org.mmarini.sound.model.SoundProcessor#createSequenceNode()
	 */
	@Override
	public SequenceNode createSequenceNode() {
		return new SequenceNode(this);
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
	 * @see org.mmarini.sound.model.SoundProcessor#createToneShiftNode()
	 */
	@Override
	public ToneShiftNode createToneShiftNode() {
		ToneShiftNode toneShiftNode = new ToneShiftNode(this);
		toneShiftNode.setFrequence(20f);
		toneShiftNode.setToneShift(12f);
		return toneShiftNode;
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#createVolumeNode()
	 */
	@Override
	public VolumeNode createVolumeNode() {
		return new VolumeNode(this);
	}

	/**
         * 
         * 
         */
	protected void fireNodeChanged(TransformNode node) {
		List<SoundListener> listeners;
		synchronized (this) {
			listeners = this.listeners;
		}
		if (listeners != null) {
			event.setTransformNode(node);
			for (SoundListener listener : listeners) {
				listener.nodeChanged(event);
			}
		}
	}

	/**
         * 
         * 
         */
	protected void fireRootChanged() {
		List<SoundListener> listeners;
		synchronized (this) {
			listeners = this.listeners;
		}
		if (listeners != null) {
			event.setTransformNode(getTransformNode());
			for (SoundListener listener : listeners) {
				listener.rootChanged(event);
			}
		}
	}

	/**
         * 
         * 
         */
	protected void fireStructureChanged(TransformNode node) {
		List<SoundListener> listeners;
		synchronized (this) {
			listeners = this.listeners;
		}
		if (listeners != null) {
			event.setTransformNode(node);
			for (SoundListener listener : listeners) {
				listener.structureChanged(event);
			}
		}
	}

	/**
	 * @return the format
	 */
	private AudioFormat getFormat() {
		return format;
	}

	/**
	 * @return
	 */
	private int getMaxLevel() {
		return 1 << (format.getSampleSizeInBits() - 1);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformerKeeper#getSampleTransformer()
	 */
	@Override
	public SampleTransformer getSampleTransformer() {
		return getTransformNode().getSampleTransformer();
	}

	/**
	 * @return the transformNode
	 */
	@Override
	public TransformNode getTransformNode() {
		return transformNode;
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#handleNodeChanged(org.mmarini.sound.model.TransformNode)
	 */
	@Override
	public void handleNodeChanged(TransformNode node) {
		getTransformNode().buildTransformer();
		fireNodeChanged(node);
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#handleStructureChanged(org.mmarini.sound.model.AbstractCompositeNode)
	 */
	@Override
	public void handleStructureChanged(AbstractCompositeNode node) {
		getTransformNode().buildTransformer();
		fireStructureChanged(node);
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#removeSoundListener(org.mmarini.sound.model.SoundListener)
	 */
	@Override
	public synchronized void removeSoundListener(SoundListener listener) {
		List<SoundListener> listeners = this.listeners;
		if (listeners != null && listeners.contains(listeners)) {
			listeners = new ArrayList<SoundListener>(listeners);
			listeners.remove(listener);
			this.listeners = listeners;
		}
	}

	/**
	 * @see org.mmarini.sound.model.SoundProcessor#setTransformNode(org.mmarini.sound.model.TransformNode)
	 */
	@Override
	public void setTransformNode(TransformNode transformNode) {
		this.transformNode = transformNode;
		getTransformNode().buildTransformer();
		fireRootChanged();
	}
}
