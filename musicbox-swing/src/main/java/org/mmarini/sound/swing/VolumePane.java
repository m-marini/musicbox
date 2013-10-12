/*
 * VolumePane.java
 *
 * $Id: VolumePane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
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
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mmarini.sound.model.VolumeNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: VolumePane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class VolumePane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private GainSlider gainSlider;

	private VolumeNode volumeControl;

	/**
         * 
         * 
         */
	public VolumePane() {
		gainSlider = new GainSlider();

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("VolumePane.title"))); //$NON-NLS-1$
		gainSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("VolumePane.gainSlider.label"))); //$NON-NLS-1$

		createContent();

		gainSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleSlideChanged();
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
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = Box.createGlue();
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
		c = gainSlider;
		layout.setConstraints(c, gbc);
		add(c);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 1;
		gbc.weighty = 1;
		c = Box.createGlue();
		layout.setConstraints(c, gbc);
		add(c);
	}

	/**
         * 
         */
	protected void handleSlideChanged() {
		float gain = gainSlider.getValue();
		volumeControl.setGain(gain);
	}

	/**
	 * @param volumeControl
	 *            the volumeControl to set
	 */
	public void setNode(VolumeNode volumeControl) {
		this.volumeControl = volumeControl;
		gainSlider.setValue(Math.round(volumeControl.getGain()));
	}
}
