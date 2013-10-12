/*
 * AutoMuter.java
 *
 * $Id: AutoMuter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AutoMuter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class AutoMuter extends LevelMeter {
	public static final int TIME_SCALE = 32;

	private int levels[];

	private int idx;

	private int count;

	private int threshold;

	private int attack;

	private int decay;

	private int size;

	/**
	 * @param scale
	 * @param threshold
	 * @param size
	 * @param decay
	 * @param attack
	 */
	public AutoMuter(int scale, int threshold, int size, int attack, int decay) {
		super(scale, 0);
		this.threshold = threshold;
		this.size = size;
		this.attack = attack;
		this.decay = decay;
		build();
	}

	/**
         * 
         */
	private void build() {
		levels = new int[size];
		for (int i = 0; i < size; ++i) {
			levels[i] = i * SCALE / (size - 1);
		}
		idx = 0;
		count = 0;
	}

	/**
	 * @see org.mmarini.sound.transfomers.LevelMeter#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		super.transform(value);
		value[0] = value[0] * levels[idx] / SCALE;
		value[1] = value[1] * levels[idx] / SCALE;
		int val = Math.max(value[0], value[1]);
		if (val > threshold) {
			if (idx < levels.length - 1) {
				count += attack;
				idx += count / TIME_SCALE;
				count %= TIME_SCALE;
				if (idx >= levels.length) {
					count = 0;
					idx = levels.length - 1;
				}
			}
		} else if (idx > 0) {
			count += decay;
			idx -= count / TIME_SCALE;
			count %= TIME_SCALE;
			if (idx <= 0) {
				count = 0;
				idx = 0;
			}
		}
	}
}
