/*
 * VolumePane.java
 *
 * $Id: MeterPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 11/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mmarini.sound.model.LevelMeterNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: MeterPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class MeterPane extends JPanel {
	private static final int REFRESH_TIME = 10;

	private static final long serialVersionUID = 1L;

	private float[] level;

	private JLevelMeter rightMeter;

	private JLevelMeter leftMeter;

	private LevelMeterNode levelMeterNode;

	private LowFrequenceSlider frequenceSlider;

	private TimeSlider timeSlider;

	private Timer timer;

	private float[] peak;

	/**
         * 
         * 
         */
	public MeterPane() {
		peak = new float[2];
		level = new float[2];
		rightMeter = new JLevelMeter();
		leftMeter = new JLevelMeter();
		frequenceSlider = new LowFrequenceSlider();
		timeSlider = new TimeSlider();

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("MeterPane.title"))); //$NON-NLS-1$
		rightMeter.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("MeterPane.rightMeter.label"))); //$NON-NLS-1$
		leftMeter.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("MeterPane.leftMeter.label"))); //$NON-NLS-1$
		frequenceSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("MeterPane.frequenceSlider.label"))); //$NON-NLS-1$
		timeSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("MeterPane.timeSlider.label"))); //$NON-NLS-1$

		timer = new Timer(REFRESH_TIME, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}

		});

		createContent();
		ChangeListener listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleFrequenceChange();
			}

		};
		frequenceSlider.addChangeListener(listener);
		listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleDelayChange();
			}

		};
		timeSlider.addChangeListener(listener);
	}

	/**
         * 
         */
	protected void createContent() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		Component c;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = leftMeter;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = rightMeter;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = frequenceSlider;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = timeSlider;
		layout.setConstraints(c, gbc);
		add(c);
	}

	/**
	 * @return the levelMeterNode
	 */
	public LevelMeterNode getLevelMeterNode() {
		return levelMeterNode;
	}

	/**
         * 
         */
	protected void handleDelayChange() {
		if (levelMeterNode != null) {
			levelMeterNode.setPeakDelay(timeSlider.getTime());
		}
	}

	/**
         * 
         */
	protected void handleFrequenceChange() {
		if (levelMeterNode != null) {
			levelMeterNode.setFrequence(frequenceSlider.getFrequence());
		}
	}

	/**
         * 
         */
	protected void refresh() {
		levelMeterNode.retrieveDecibelLevel(level);
		levelMeterNode.retrieveDecibelPeak(peak);
		rightMeter.setValue(level[0]);
		rightMeter.setPeak(peak[0]);
		leftMeter.setValue(level[1]);
		leftMeter.setPeak(peak[1]);
	}

	/**
	 * @param node
	 *            the levelMeterNode to set
	 */
	public void setNode(LevelMeterNode node) {
		this.levelMeterNode = node;
		if (node != null) {
			frequenceSlider.setFrequence(node.getFrequence());
			timeSlider.setTime(node.getPeakDelay());
			timer.start();
		} else {
			timer.stop();
		}
	}
}
