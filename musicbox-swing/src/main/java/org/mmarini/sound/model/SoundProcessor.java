/*
 * Processor.java
 *
 * $Id: SoundProcessor.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.AutoMuter;
import org.mmarini.sound.transfomers.DecibelLevelMeter;
import org.mmarini.sound.transfomers.Echo;
import org.mmarini.sound.transfomers.Mixer;
import org.mmarini.sound.transfomers.Modulator;
import org.mmarini.sound.transfomers.SampleTransformer;
import org.mmarini.sound.transfomers.SampleTransformerKeeper;
import org.mmarini.sound.transfomers.Sequencer;
import org.mmarini.sound.transfomers.ToneShifter;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SoundProcessor.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public interface SoundProcessor extends SampleTransformerKeeper {
	/**
	 * 
	 * @param listener
	 */
	public abstract void addSoundListener(SoundListener listener);

	/**
	 * @return
	 */
	public abstract AutoMuteNode createAutoMuteNode();

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
	 * 
	 * @return
	 */
	public abstract ConnectNode createConnectNode();

	/**
	 * @param delay
	 * @param echoLevel
	 * @param signalLevel
	 * @return
	 */
	public abstract Echo createEcho(float delay, float echoLevel,
			float signalLevel);

	/**
	 * @return
	 */
	public abstract EchoNode createEchoNode();

	/**
	 * @param frequence
	 * @param peakDelay
	 * @return
	 */
	public abstract DecibelLevelMeter createLevelMeter(float frequence,
			float peakDelay);

	/**
	 * @return
	 */
	public abstract LevelMeterNode createLevelMeterNode();

	/**
	 * @param transformers
	 * @return
	 */
	public abstract Mixer createMixer(SampleTransformer[] transformers);

	/**
	 * @return
	 */
	public abstract MixerNode createMixerNode();

	/**
	 * @return
	 */
	public abstract ModulateNode createModulateNode();

	/**
	 * @param frequence
	 * @param amplitude
	 * @param offset
	 * @return
	 */
	public abstract Modulator createMoulator(float frequence, float amplitude,
			float offset);

	/**
	 * @return
	 */
	public abstract SequenceNode createSequenceNode();

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

	/**
	 * @return
	 */
	public abstract ToneShiftNode createToneShiftNode();

	/**
	 * @return
	 */
	public abstract VolumeNode createVolumeNode();

	/**
	 * @return
	 */
	public abstract TransformNode getTransformNode();

	/**
	 * @param node
	 */
	public abstract void handleNodeChanged(TransformNode node);

	/**
	 * @param node
	 */
	public abstract void handleStructureChanged(AbstractCompositeNode node);

	/**
	 * 
	 * @param listener
	 */
	public abstract void removeSoundListener(SoundListener listener);

	/**
	 * @param transformNode
	 */
	public abstract void setTransformNode(TransformNode transformNode);
}
