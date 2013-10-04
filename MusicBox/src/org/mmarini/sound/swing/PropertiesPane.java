/*
 * PropertiesPane.java
 *
 * $Id: PropertiesPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.CardLayout;

import javax.swing.JPanel;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.AutoMuteNode;
import org.mmarini.sound.model.ConnectNode;
import org.mmarini.sound.model.EchoNode;
import org.mmarini.sound.model.LevelMeterNode;
import org.mmarini.sound.model.ModulateNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.ToneShiftNode;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.model.TransformNodeVisitor;
import org.mmarini.sound.model.VolumeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: PropertiesPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class PropertiesPane extends JPanel implements TransformNodeVisitor {
	/**
         * 
         */
	private static final String AUTO_MUTE_CARD = "AutoMute";

	/**
         * 
         */
	private static final String TONE_SHIFT = "ToneShift";

	/**
         * 
         */
	private static final String MODULATOR_CARD = "modulator";

	/**
         * 
         */
	private static final String VOLUME_CARD = "volume";

	/**
         * 
         */
	private static final String DESCRIPTION_CARD = "description";

	/**
         * 
         */
	private static final String METER_CARD = "meter";

	/**
         * 
         */
	private static final String ECHO_CARD = "echo";

	private static Logger log = LoggerFactory.getLogger(PropertiesPane.class);

	private VolumePane volumePane;

	private CardLayout layout;

	private DescriptionPane descriptionPane;

	private MeterPane meterPane;

	private EchoPane echoPane;

	private ModulatorPane modulatorPane;

	private ToneShiftPane toneShiftPane;

	private AutoMutePane autoMutePane;

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         */
	public PropertiesPane() {
		init();
	}

	/**
         * 
         */
	private void init() {
		log.debug("init");
		volumePane = new VolumePane();
		layout = new CardLayout();
		descriptionPane = new DescriptionPane();
		meterPane = new MeterPane();
		echoPane = new EchoPane();
		modulatorPane = new ModulatorPane();
		toneShiftPane = new ToneShiftPane();
		autoMutePane = new AutoMutePane();

		setLayout(layout);
		add(volumePane, VOLUME_CARD);
		add(descriptionPane, DESCRIPTION_CARD);
		add(meterPane, METER_CARD);
		add(echoPane, ECHO_CARD);
		add(modulatorPane, MODULATOR_CARD);
		add(toneShiftPane, TONE_SHIFT);
		add(autoMutePane, AUTO_MUTE_CARD);
		layout.show(this, METER_CARD);
	}

	/**
	 * @param transformNode
	 */
	public void showTransformer(TransformNode transformNode) {
		meterPane.setNode(null);
		transformNode.apply(this);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Mixer)
	 */
	@Override
	public void visit(AbstractCompositeNode node) {
		descriptionPane.setDescription("Mixer");
		layout.show(this, DESCRIPTION_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.AutoMuteNode)
	 */
	@Override
	public void visit(AutoMuteNode node) {
		autoMutePane.setNode(node);
		layout.show(this, AUTO_MUTE_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ConnectTransformer)
	 */
	@Override
	public void visit(ConnectNode node) {
		descriptionPane.setDescription("Connector");
		layout.show(this, DESCRIPTION_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Echo)
	 */
	@Override
	public void visit(EchoNode node) {
		echoPane.setNode(node);
		layout.show(this, ECHO_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.LevelMeter)
	 */
	@Override
	public void visit(LevelMeterNode node) {
		meterPane.setNode(node);
		layout.show(this, METER_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Modulator)
	 */
	@Override
	public void visit(ModulateNode node) {
		modulatorPane.setNode(node);
		layout.show(this, MODULATOR_CARD);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Sequencer)
	 */
	@Override
	public void visit(SequenceNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ToneShifter)
	 */
	@Override
	public void visit(ToneShiftNode node) {
		toneShiftPane.setNode(node);
		layout.show(this, TONE_SHIFT);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.VolumeControl)
	 */
	@Override
	public void visit(VolumeNode node) {
		volumePane.setNode(node);
		layout.show(this, VOLUME_CARD);
	}
}
