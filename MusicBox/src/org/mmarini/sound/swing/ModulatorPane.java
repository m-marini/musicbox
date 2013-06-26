/*
 * VolumePane.java
 *
 * $Id: ModulatorPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
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
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mmarini.sound.model.ModulateNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ModulatorPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class ModulatorPane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private MidFrequenceSlider frequenceSlider;

	private JSlider amplitudeSlider;

	private JSlider offsetSlider;

	private ModulateNode node;

	/**
         * 
         * 
         */
	public ModulatorPane() {
		frequenceSlider = new MidFrequenceSlider();
		amplitudeSlider = new JSlider(SwingConstants.VERTICAL);
		offsetSlider = new JSlider(SwingConstants.VERTICAL);

		amplitudeSlider.setMinimum(0);
		amplitudeSlider.setMaximum(100);
		amplitudeSlider.setMinorTickSpacing(5);
		amplitudeSlider.setMajorTickSpacing(10);
		amplitudeSlider.setPaintLabels(true);
		amplitudeSlider.setPaintTicks(true);

		offsetSlider.setMinimum(0);
		offsetSlider.setMaximum(100);
		offsetSlider.setMinorTickSpacing(5);
		offsetSlider.setMajorTickSpacing(10);
		offsetSlider.setPaintLabels(true);
		offsetSlider.setPaintTicks(true);

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ModulatorPane.title"))); //$NON-NLS-1$
		amplitudeSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ModulatorPane.amplitudeSlider.label"))); //$NON-NLS-1$
		offsetSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ModulatorPane.offsetSlider.label"))); //$NON-NLS-1$
		frequenceSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ModulatorPane.frequenceSlider.label"))); //$NON-NLS-1$
		createContent();

		frequenceSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleFrequenceChange();
			}

		});
		amplitudeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleAmplitudeChange();
			}

		});
		offsetSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleOffsetChange();
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
		c = frequenceSlider;
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
		c = amplitudeSlider;
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
		c = offsetSlider;
		layout.setConstraints(c, gbc);
		add(c);
	}

	/**
         * 
         */
	protected void handleAmplitudeChange() {
		if (node != null) {
			node.setAmplitude((float) amplitudeSlider.getValue() / 100);
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
	protected void handleOffsetChange() {
		if (node != null) {
			node.setOffset((float) offsetSlider.getValue() / 100);
		}
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(ModulateNode node) {
		this.node = node;
		frequenceSlider.setFrequence(node.getFrequence());
		amplitudeSlider.setValue(Math.round(node.getAmplitude() * 100));
		offsetSlider.setValue(Math.round(node.getOffset() * 100));
	}
}
