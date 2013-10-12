/*
 * Echo.java
 *
 * $Id: Echo.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 12/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import java.io.Serializable;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Echo.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class Echo implements SampleTransformer, Serializable {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         */
	public static final int LEVEL = 64 * 1024;

	private int idx;

	private int size;

	private short[][] buffer;

	private int gainLevel;

	private int echoLevel;

	/**
         * 
         */
	public Echo() {
		this(4000, LEVEL / 2, LEVEL / 2);
	}

	/**
         * 
         */

	/**
	 * @param size
	 * @param gainLevel
	 * @param echoLevel
	 */
	public Echo(int size, int gainLevel, int echoLevel) {
		this.size = Math.max(1, size);
		this.gainLevel = gainLevel;
		this.echoLevel = echoLevel;
		buffer = new short[this.size][2];
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] sample) {
		sample[0] = (sample[0] * gainLevel + buffer[idx][0] * echoLevel)
				/ LEVEL;
		sample[1] = (sample[1] * gainLevel + buffer[idx][1] * echoLevel)
				/ LEVEL;
		buffer[idx][0] = (short) sample[0];
		buffer[idx++][1] = (short) sample[1];
		if (idx >= size)
			idx = 0;
	}
}
