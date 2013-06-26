/*
 * Palette.java
 *
 * $Id: Palette.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Palette.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class Palette extends JToolBar {

	class EntryChangeListener implements ChangeListener {
		private PaletteEntry entry;

		/**
		 * @param entry
		 */
		public EntryChangeListener(PaletteEntry entry) {
			this.entry = entry;
		}

		/**
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			AbstractButton source = (AbstractButton) e.getSource();
			if (source.isSelected()) {
				setSelectedEntry(entry);
			}
		}
	}

	private static Log log = LogFactory.getLog(Palette.class);

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private ButtonGroup group = new ButtonGroup();

	private PaletteEntry selectedEntry;

	private SoundProcessor soundProcessor;;

	/**
         * 
         */
	public Palette() {
		super(SwingConstants.VERTICAL);
		init();
	}

	/**
	 * 
	 * @return
	 */
	public TransformNode createSelectedNode() {
		TransformNode node = null;
		PaletteEntry entry = getSelectedEntry();
		if (entry != null)
			node = entry.createSampleTransformer(getSoundProcessor());
		return node;
	}

	/**
	 * @return the group
	 */
	private ButtonGroup getGroup() {
		return group;
	}

	/**
	 * @return the selectedEntry
	 */
	private PaletteEntry getSelectedEntry() {
		return selectedEntry;
	}

	/**
	 * @return the soundProcessor
	 */
	public SoundProcessor getSoundProcessor() {
		return soundProcessor;
	}

	/**
         * 
         * 
         */
	protected void init() {
		log.debug("init");
		initEntry(new ArrowPaletteEntry(), true);
		initEntry(new ConnectPaletteEntry(), false);
		initEntry(new VolumePaletteEntry(), false);
		initEntry(new LevelMeterPaletteEntry(), false);
		initEntry(new AutoMutePaletteEntry(), false);
		initEntry(new EchoPaletteEntry(), false);
		initEntry(new ToneShiftPaletteEntry(), false);
		initEntry(new ModulatePaletteEntry(), false);
		initEntry(new MixerPaletteEntry(), false);
	}

	private void initEntry(PaletteEntry entry, boolean enabled) {
		AbstractButton btn = entry.getButton();
		btn.addChangeListener(new EntryChangeListener(entry));
		getGroup().add(btn);
		add(btn);
		btn.setSelected(enabled);
	}

	/**
	 * @param entry
	 */
	private void setSelectedEntry(PaletteEntry entry) {
		this.selectedEntry = entry;
	}

	/**
	 * @param soundProcessor
	 *            the soundProcessor to set
	 */
	public void setSoundProcessor(SoundProcessor soundProcessor) {
		this.soundProcessor = soundProcessor;
	}
}
