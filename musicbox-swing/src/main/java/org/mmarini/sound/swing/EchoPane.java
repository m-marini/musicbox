/*
 * VolumePane.java
 *
 * $Id: EchoPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
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

import org.mmarini.sound.model.EchoNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: EchoPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class EchoPane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private GainSlider signalSlider;

	private GainSlider echoSlider;

	private TimeSlider delaySlider;

	private EchoNode echoNode;

	/**
         * 
         * 
         */
	public EchoPane() {
		signalSlider = new GainSlider();
		echoSlider = new GainSlider();
		delaySlider = new TimeSlider();

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("EchoPane.title"))); //$NON-NLS-1$
		signalSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("EchoPane.signalSlider.label"))); //$NON-NLS-1$
		echoSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("EchoPane.echoSlider.label"))); //$NON-NLS-1$
		delaySlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("EchoPane.delaySlider.label"))); //$NON-NLS-1$

		createContent();
		signalSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleSignalChanged();
			}

		});
		echoSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleEchoChanged();
			}

		});
		delaySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleDelayChanged();
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
		c = signalSlider;
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
		c = echoSlider;
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
		c = delaySlider;
		layout.setConstraints(c, gbc);
		add(c);
	}

	/**
         * 
         */
	protected void handleDelayChanged() {
		if (echoNode != null)
			echoNode.setDelay(delaySlider.getTime());
	}

	/**
         * 
         */
	protected void handleEchoChanged() {
		if (echoNode != null)
			echoNode.setEchoLevel(echoSlider.getGain());
	}

	/**
         * 
         */
	protected void handleSignalChanged() {
		if (echoNode != null)
			echoNode.setSignalLevel(signalSlider.getGain());
	}

	/**
	 * @param echoNode
	 *            the echoNode to set
	 */
	public void setNode(EchoNode echoNode) {
		this.echoNode = null;
		signalSlider.setGain(echoNode.getSignalLevel());
		echoSlider.setGain(echoNode.getEchoLevel());
		delaySlider.setTime(echoNode.getDelay());
		this.echoNode = echoNode;
	}
}
