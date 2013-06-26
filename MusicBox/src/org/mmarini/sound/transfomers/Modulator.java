/*
 * EnvelopeEffect.java
 *
 * $Id: Modulator.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 12/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Modulator.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class Modulator implements SampleTransformer {
	public static final int LEVEL = 64 * 1024;

	private int idx;

	private int envelope[];

	private int amplitude;

	private int offset;

	private int size;

	/**
	 * @param size
	 * @param amplitude
	 * @param offset
	 */
	public Modulator(int size, int amplitude, int offset) {
		this.amplitude = amplitude;
		this.offset = offset;
		this.size = size;
		build();
	}

	/**
         * 
         */
	private void build() {
		idx = 0;
		envelope = new int[size];
		for (int i = 0; i < size; ++i) {
			double env = Math.sin(2. * Math.PI * i / size);
			envelope[i] = (int) Math.round(env * amplitude + offset);
		}
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] sample) {
		sample[0] = sample[0] * envelope[idx] / LEVEL;
		sample[1] = sample[1] * envelope[idx++] / LEVEL;
		if (idx >= envelope.length)
			idx = 0;
	}
}