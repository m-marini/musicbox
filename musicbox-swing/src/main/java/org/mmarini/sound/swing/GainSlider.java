/*
 * GainSlider.java
 *
 * $Id: GainSlider.java,v 1.2 2008/11/20 21:07:25 marco Exp $
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
 * @version $Id: GainSlider.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class GainSlider extends JSlider {
	private static final long serialVersionUID = 1L;

	private static final int MAJOR_TICK = 12;

	private static final int MINOR_TICK = 3;

	private static final int MAXIMUM = +12;

	private static final int MINIMUM = -96;

	/**
         * 
         */
	public GainSlider() {
		init();
	}

	/**
	 * 
	 * @return
	 */
	public float getGain() {
		return getValue();
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
		setSnapToTicks(true);
	}

	/**
	 * 
	 * @param gain
	 */
	public void setGain(float gain) {
		setValue(Math.round(gain));
	}
}
