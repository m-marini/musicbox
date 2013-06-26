/*
 * JLevelMeter.java
 *
 * $Id: JLevelMeter.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 11/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: JLevelMeter.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class JLevelMeter extends JComponent {

	private static final int LIGHT_SPACE_HEIGHT = 1;

	private static final int LIGHT_SPACE_WIDTH = 2;

	private static final int MIN_BAR_HEIGHT = 101;

	private static final int TICK_SPACE_WIDTH = 3;

	private static final int MAJOR_TICK_WIDTH = 6;

	private static final int MINOR_TICK_WIDTH = 3;

	private static final int BAR_WIDTH = 20;

	private static final Color RED_LEVEL_COLOR = Color.RED;

	private static final Color YELLOW_LEVEL_COLOR = Color.YELLOW;

	private static final Color GREEN_LEVEL_COLOR = Color.GREEN;

	private static final Color[] DARK_COLORS = new Color[] {
			GREEN_LEVEL_COLOR.darker(), YELLOW_LEVEL_COLOR.darker(),
			RED_LEVEL_COLOR.darker() };

	private static final Color[] BRIGHT_COLORS = new Color[] {
			GREEN_LEVEL_COLOR.brighter(), YELLOW_LEVEL_COLOR.brighter(),
			RED_LEVEL_COLOR.brighter() };

	private static final Color BACKGROUND_LEVEL_COLOR = Color.BLACK;

	private static final long serialVersionUID = 1L;

	private int value;

	private int peak;

	private int minimum;

	private int maximum;

	private int greenLevel;

	private int redLevel;

	private String[] labels;

	private int majorTick;

	private int minorTick;

	/**
         * 
         */
	public JLevelMeter() {
		init();
	}

	/**
	 * @param value
	 * @return
	 */
	private int calculateLimitedValue(float value) {
		int v;
		int min = getMinimum();
		int max = getMaximum();
		if (value < min)
			v = min;
		else if (value > max)
			v = max;
		else
			v = Math.round(value);
		return v;
	}

	/**
	 * @param value
	 * @return
	 */
	private int calculateLimitedValue(int value) {
		return Math.max(getMinimum(), Math.min(value, getMaximum()));
	}

	/**
	 * @return
	 */
	private void createLabels() {
		int m = getMajorTick();
		int n = (getMaximum() - getMinimum()) / m + 1;
		String[] labels = new String[n];
		for (int i = 0; i < n; ++i) {
			int v = getMinimum() + i * m;
			NumberFormat fmt = NumberFormat.getNumberInstance();
			labels[i] = fmt.format(v);
		}
		setLabels(labels);
	}

	/**
	 * @return
	 */
	private Dimension createMinimumSize() {
		String[] labels = getLabels();
		int w = 0;
		int n = labels.length;
		FontMetrics fontMetrics = getFontMetrics(getFont());
		for (int i = 0; i < n; ++i) {
			String label = labels[i];
			w = Math.max(fontMetrics.stringWidth(label), w);
		}
		w += BAR_WIDTH + MAJOR_TICK_WIDTH + TICK_SPACE_WIDTH;
		Dimension size = new Dimension(w, MIN_BAR_HEIGHT);
		return size;
	}

	/**
	 * @return the greenLevel
	 */
	private int getGreenLevel() {
		return greenLevel;
	}

	/**
	 * @return the labels
	 */
	private String[] getLabels() {
		return labels;
	}

	/**
	 * @return the majorTick
	 */
	private int getMajorTick() {
		return majorTick;
	}

	/**
	 * @return the maximum
	 */
	private int getMaximum() {
		return maximum;
	}

	/**
	 * @return the minimum
	 */
	private int getMinimum() {
		return minimum;
	}

	/**
	 * @return the minorTick
	 */
	private int getMinorTick() {
		return minorTick;
	}

	/**
	 * @return the peak
	 */
	private int getPeak() {
		return peak;
	}

	/**
	 * @return the redLevel
	 */
	private int getRedLevel() {
		return redLevel;
	}

	/**
	 * @return the value
	 */
	private int getValue() {
		return value;
	}

	/**
         * 
         */
	private void init() {
		setMinimum(-96);
		setMaximum(0);
		setRedLevel(-3);
		setGreenLevel(-6);
		setMinorTick(3);
		setMajorTick(6);
		createLabels();
	}

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void paintBar(Graphics g, int x, int y, int w, int h) {
		g.setColor(BACKGROUND_LEVEL_COLOR);
		g.fillRect(x, y, w, h);
		paintBar(getPeak(), g, x + LIGHT_SPACE_WIDTH, y + LIGHT_SPACE_HEIGHT, w
				- 2 * LIGHT_SPACE_WIDTH, h - LIGHT_SPACE_HEIGHT * 2,
				DARK_COLORS);
		paintBar(getValue(), g, x + LIGHT_SPACE_WIDTH, y + LIGHT_SPACE_HEIGHT,
				w - LIGHT_SPACE_WIDTH * 2, h - LIGHT_SPACE_HEIGHT * 2,
				BRIGHT_COLORS);
	}

	/**
	 * @param value
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void paintBar(int value, Graphics g, int x, int y, int w, int h,
			Color[] colors) {
		int gl = getGreenLevel();
		int min = getMinimum();
		int max = getMaximum();
		int range = max - min;
		int rl = getRedLevel();
		int rh = h * (rl - min) / range;
		int gh = h * (gl - min) / range;
		int vh = h * (value - min) / range;
		for (int ys = 0; ys < vh; ys += 2) {
			if (ys < gh)
				g.setColor(colors[0]);
			else if (ys < rh)
				g.setColor(colors[1]);
			else
				g.setColor(colors[2]);
			g.drawLine(x, y - ys + h - 1, x + w - 1, y - ys + h - 1);
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = getSize();
		Border border = getBorder();
		Insets insets = border.getBorderInsets(this);
		int x = insets.left;
		int y = insets.top;
		int w = size.width - x - insets.right;
		int h = size.height - y - insets.bottom;
		Dimension minSize = createMinimumSize();
		if (w > minSize.width && h > minSize.height) {
			x += (w - minSize.width) / 2;
			FontMetrics fontMetrics = getFontMetrics(getFont());
			int fh = fontMetrics.getHeight();
			paintBar(g, x, y + fh - fontMetrics.getAscent() / 2, BAR_WIDTH, h
					- fh);
			paintTicks(g, x + BAR_WIDTH, y, w, h);
			paintLabels(g, x + BAR_WIDTH + MAJOR_TICK_WIDTH + TICK_SPACE_WIDTH,
					y, w, h);
		}
	}

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void paintLabels(Graphics g, int x, int y, int w, int h) {
		int max = getMaximum();
		int min = getMinimum();
		int range = max - min;
		g.setColor(Color.BLACK);
		int step = getMajorTick();
		String[] labels = getLabels();
		int idx = 0;
		int h1 = (h - 3 - getFontMetrics(getFont()).getHeight());
		int h2 = h - 2;
		for (int i = 0; i <= range; i += step) {
			int ys = y + h2 - i * h1 / range;
			g.drawString(labels[idx++], x, ys);
		}
	}

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void paintTicks(Graphics g, int x, int y, int w, int h) {
		int max = getMaximum();
		int min = getMinimum();
		int range = max - min;
		g.setColor(Color.BLACK);
		int step = getMinorTick();
		FontMetrics fontMetrics = getFontMetrics(getFont());
		int h1 = (h - 3 - fontMetrics.getHeight());
		int h2 = h - 2 - fontMetrics.getAscent() / 2;
		for (int i = 0; i <= range; i += step) {
			int ys = y + h2 - i * h1 / range;
			g.drawLine(x, ys, x + MINOR_TICK_WIDTH, ys);
		}
		step = getMajorTick();
		for (int i = 0; i <= range; i += step) {
			int ys = y + h2 - i * h1 / range;
			g.drawLine(x, ys, x + MAJOR_TICK_WIDTH, ys);
		}
	}

	/**
	 * @param greenLevel
	 *            the greenLevel to set
	 */
	private void setGreenLevel(int greenLevel) {
		this.greenLevel = greenLevel;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	private void setLabels(String[] labels) {
		this.labels = labels;
	}

	/**
	 * @param majorTick
	 *            the majorTick to set
	 */
	private void setMajorTick(int majorTick) {
		this.majorTick = majorTick;
	}

	/**
	 * @param maximum
	 *            the maximum to set
	 */
	private void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	/**
	 * @param minimum
	 *            the minimum to set
	 */
	private void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	/**
	 * @param minorTick
	 *            the minorTick to set
	 */
	private void setMinorTick(int minorTick) {
		this.minorTick = minorTick;
	}

	/**
	 * @param value
	 */
	public void setPeak(float value) {
		int v = calculateLimitedValue(value);
		setPeak(v);
	}

	/**
	 * @param peak
	 *            the peak to set
	 */
	private void setPeak(int peak) {
		this.peak = calculateLimitedValue(peak);
		repaint();
	}

	/**
	 * @param redLevel
	 *            the redLevel to set
	 */
	private void setRedLevel(int redLevel) {
		this.redLevel = redLevel;
	}

	/**
	 * @param value
	 */
	public void setValue(float value) {
		int v = calculateLimitedValue(value);
		setValue(v);
	}

	/**
	 * @param value
	 *            the value to set
	 */
	private void setValue(int value) {
		this.value = calculateLimitedValue(value);
		repaint();
	}
}
