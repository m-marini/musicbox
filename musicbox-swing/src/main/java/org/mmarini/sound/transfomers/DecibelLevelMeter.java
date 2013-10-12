/*
 * JLevelMeter.java
 *
 * $Id: DecibelLevelMeter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: DecibelLevelMeter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class DecibelLevelMeter extends LevelMeter {

	private int maxLevel;

	/**
	 * 
	 * @param scale
	 * @param persistence
	 * @param maxLevel
	 */
	public DecibelLevelMeter(int scale, int persistence, int maxLevel) {
		super(scale, persistence);
		this.maxLevel = maxLevel;
	}

	/**
	 * @param value
	 * @return
	 */
	private float convertToDecibel(int value) {
		return (float) (20. * Math.log10((double) value / maxLevel));
	}

	/**
	 * @return
	 */
	public void retrieveDecibelLevel(float[] values) {
		int[] value = getLevel();
		values[0] = convertToDecibel(value[0]);
		values[1] = convertToDecibel(value[1]);
	}

	/**
	 * @return
	 */
	public void retrieveDecibelPeak(float[] values) {
		int[] value = getPeak();
		values[0] = convertToDecibel(value[0]);
		values[1] = convertToDecibel(value[1]);
	}
}
