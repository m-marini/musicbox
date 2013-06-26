/*
 * GainSlider.java
 *
 * $Id: TimeSlider.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 *
 * 11/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: TimeSlider.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class TimeSlider extends JSlider {

	/**
         * 
         */
	private static final int MAJOR_TICK = 50;

	/**
         * 
         */
	private static final int MINOR_TICK = 10;

	/**
         * 
         */
	private static final int MAXIMUM = 500;

	/**
         * 
         */
	private static final int MINIMUM = 0;

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         */
	public TimeSlider() {
		init();
	}

	/**
	 * 
	 * @return
	 */
	public float getTime() {
		int value = getValue();
		return value / 1000f;
	}

	/**
         * 
         */
	private void init() {
		setOrientation(SwingConstants.VERTICAL);
		setMinimum(MINIMUM);
		setMaximum(MAXIMUM);
		setMinorTickSpacing(MINOR_TICK);
		setMajorTickSpacing(MAJOR_TICK);
		setPaintTicks(true);
		setPaintLabels(true);
	}

	/**
	 * 
	 * @param time
	 */
	public void setTime(float time) {
		int value = Math.round(time * 1000);
		setValue(value);
	}
}
