/*
 * GainSlider.java
 *
 * $Id: LowFrequenceSlider.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 *
 * 11/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.text.NumberFormat;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LowFrequenceSlider.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class LowFrequenceSlider extends JSlider {

	/**
         * 
         */
	private static final int SCALE = 10;

	/**
         * 
         */
	private static final float MAXIMUM = 100f;

	/**
         * 
         */
	private static final float MINIMUM = 1;

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         */
	public LowFrequenceSlider() {
		init();
	}

	/**
	 * @param value
	 * @return
	 */
	private int calculateLogValue(float value) {
		return (int) Math.round(Math.log10(value) * SCALE);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private float calculatePowValue(int value) {
		return (float) Math.pow(10, (double) value / SCALE);
	}

	/**
	 * @return
	 */
	private Hashtable<Integer, JComponent> createLabels() {
		Hashtable<Integer, JComponent> label = new Hashtable<Integer, JComponent>(
				0);
		NumberFormat fmt = NumberFormat.getNumberInstance();
		for (float v = MINIMUM; v <= MAXIMUM; v *= SCALE) {
			int vv = calculateLogValue(v);
			label.put(vv, new JLabel(fmt.format(v)));
		}
		return label;
	}

	/**
	 * 
	 * @return
	 */
	public float getFrequence() {
		return calculatePowValue(getValue());
	}

	/**
         * 
         */
	private void init() {
		setOrientation(SwingConstants.VERTICAL);
		rebuild();
		setMajorTickSpacing(SCALE);
		setPaintTicks(true);
		setPaintLabels(true);
	}

	/**
         * 
         * 
         */
	private void rebuild() {
		setLogMinimum(MINIMUM);
		setLogMaximum(MAXIMUM);
		setLabelTable(createLabels());
	}

	/**
	 * 
	 * @param frequence
	 */
	public void setFrequence(float frequence) {
		setValue(calculateLogValue(frequence));
	}

	/**
	 * @param maximum
	 */
	private void setLogMaximum(float maximum) {
		int value = calculateLogValue(maximum);
		setMaximum(value);
	}

	/**
	 * @param minimum
	 */
	private void setLogMinimum(float minimum) {
		int value = calculateLogValue(minimum);
		setMinimum(value);
	}
}
