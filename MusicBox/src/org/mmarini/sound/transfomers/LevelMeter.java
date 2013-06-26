/*
 * JLevelMeter.java
 *
 * $Id: LevelMeter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LevelMeter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class LevelMeter implements SampleTransformer {
	public static int SCALE = 64 * 1024;

	private int[] level = new int[2];

	private int[] peak = new int[2];

	private int scale;

	private int scaleLevel;

	private int timeout0;

	private int timeout1;

	private int persistence;

	/**
	 * 
	 * @param scale
	 * @param persistence
	 * @param maxLevel
	 */
	public LevelMeter(int scale, int persistence) {
		this.scale = scale;
		this.persistence = persistence;
	}

	/**
	 * @return the level
	 */
	public int[] getLevel() {
		return level;
	}

	/**
	 * @return the peak
	 */
	public int[] getPeak() {
		return peak;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		int lev = Math.abs(value[0]);
		level[0] = (level[0] * scaleLevel + lev * scale) / SCALE;
		if (lev >= peak[0]) {
			peak[0] = lev;
			timeout0 = persistence;
		} else if (timeout0 > 0) {
			--timeout0;
		} else {
			--peak[0];
		}

		lev = Math.abs(value[1]);
		level[1] = (level[1] * scaleLevel + lev * scale) / SCALE;
		if (lev >= peak[1]) {
			peak[1] = lev;
			timeout1 = persistence;
		} else if (timeout1 > 0) {
			--timeout1;
		} else {
			--peak[1];
		}
	}
}
