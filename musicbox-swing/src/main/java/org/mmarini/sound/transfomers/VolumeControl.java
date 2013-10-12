/*
 * JLevelMeter.java
 *
 * $Id: VolumeControl.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import java.io.Serializable;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: VolumeControl.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class VolumeControl implements SampleTransformer, Serializable {
	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	public static int SCALE = 64 * 1024;

	private int scale;

	/**
         * 
         */
	public VolumeControl() {
	}

	/**
	 * @param scale
	 */
	public VolumeControl(int scale) {
		setScale(scale);
	}

	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		value[0] = value[0] * scale / SCALE;
		value[1] = value[1] * scale / SCALE;
	}
}
