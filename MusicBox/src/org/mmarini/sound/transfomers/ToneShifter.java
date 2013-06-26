/*
 * ToneShifter.java
 *
 * $Id: ToneShifter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 13/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ToneShifter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class ToneShifter implements SampleTransformer {
	public static int TIME_SCALE = 64 * 1024;

	private int idxPush;

	private int idxPop;

	private int size;

	private short[][] buffer;

	private int count;

	private int shift;

	/**
         * 
         */
	public ToneShifter() {
		this(1, 1);
	}

	/**
	 * @param size
	 * @param shift
	 */
	public ToneShifter(int size, int shift) {
		this.shift = shift;
		this.size = size;
		buffer = new short[size][2];
		idxPush = 0;
		idxPop = 1;
		count = 0;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		buffer[idxPush][0] = (short) value[0];
		buffer[idxPush][1] = (short) value[1];
		if (++idxPush >= size)
			idxPush = 0;
		value[0] = buffer[idxPop][0];
		value[1] = buffer[idxPop][1];
		if ((count += shift) >= TIME_SCALE) {
			if ((idxPop += (count / TIME_SCALE)) >= size)
				idxPop = 0;
			count %= TIME_SCALE;
		}
	}
}
