/*
 * VolumePane.java
 *
 * $Id: ToneShiftPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
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

import org.mmarini.sound.model.ToneShiftNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ToneShiftPane.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class ToneShiftPane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private LowFrequenceSlider frequenceSlider;

	private JSlider toneSlider;

	private ToneShiftNode node;

	/**
         * 
         * 
         */
	public ToneShiftPane() {
		frequenceSlider = new LowFrequenceSlider();
		toneSlider = new JSlider(SwingConstants.VERTICAL);

		toneSlider.setMinimum(-24);
		toneSlider.setMaximum(24);
		toneSlider.setMinorTickSpacing(1);
		toneSlider.setMajorTickSpacing(12);
		toneSlider.setPaintLabels(true);
		toneSlider.setPaintTicks(true);
		toneSlider.setSnapToTicks(true);

		setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ToneShiftPane.title"))); //$NON-NLS-1$
		toneSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ToneShiftPane.toneSlider.label"))); //$NON-NLS-1$
		frequenceSlider.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("ToneShiftPane.frequenceSlider.label"))); //$NON-NLS-1$
		createContent();

		frequenceSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleFrequenceChange();
			}

		});
		toneSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				handleToneChange();
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
		c = toneSlider;
		layout.setConstraints(c, gbc);
		add(c);
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
	protected void handleToneChange() {
		if (node != null) {
			node.setToneShift(toneSlider.getValue());
		}
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(ToneShiftNode node) {
		this.node = node;
		frequenceSlider.setFrequence(node.getFrequence());
		toneSlider.setValue(Math.round(node.getToneShift()));
	}
}
