/*
 * VolumePane.java
 *
 * $Id: AutoMutePane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mmarini.sound.model.AutoMuteNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AutoMutePane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class AutoMutePane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private LowFrequenceSlider frequenceSlider;

	private TimeSlider attackSlider;

	private TimeSlider decaySlider;

	private GainSlider thresholdSlider;

	private AutoMuteNode node;

	/**
         * 
         * 
         */
	public AutoMutePane() {
		frequenceSlider = new LowFrequenceSlider();
		attackSlider = new TimeSlider();
		decaySlider = new TimeSlider();
		thresholdSlider = new GainSlider();

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("AutoMutePane.title"))); //$NON-NLS-1$
		frequenceSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("AutoMutePane.frequenceSlider.label"))); //$NON-NLS-1$
		attackSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("AutoMutePane.attackSlider.label"))); //$NON-NLS-1$
		decaySlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("AutoMutePane.decaySlider.label"))); //$NON-NLS-1$
		thresholdSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("AutoMutePane.thresholdSlider.label"))); //$NON-NLS-1$

		createContent();

		frequenceSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleFrequenceChange();
			}

		});

		attackSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleAttackChange();
			}

		});

		decaySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleDecayChange();
			}

		});

		thresholdSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleThresholdChange();
			}

		});
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
		c = thresholdSlider;
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
		c = attackSlider;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = decaySlider;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = frequenceSlider;
		layout.setConstraints(c, gbc);
		add(c);
	}

	/**
         * 
         */
	protected void handleAttackChange() {
		if (node != null) {
			node.setAttackTime(attackSlider.getTime());
		}
	}

	/**
         * 
         */
	protected void handleDecayChange() {
		if (node != null) {
			node.setDecayTime(decaySlider.getTime());
		}
	}

	/**
         * 
         */
	protected void handleFrequenceChange() {
		if (node != null) {
			node.setFrequence(frequenceSlider.getFrequence());
		}
	}

	/**
         * 
         */
	protected void handleThresholdChange() {
		if (node != null) {
			node.setThreshold(thresholdSlider.getGain());
		}
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(AutoMuteNode node) {
		this.node = node;
		frequenceSlider.setFrequence(node.getFrequence());
		attackSlider.setTime(node.getAttackTime());
		decaySlider.setTime(node.getDecayTime());
		thresholdSlider.setGain(node.getThreshold());
	}
}
