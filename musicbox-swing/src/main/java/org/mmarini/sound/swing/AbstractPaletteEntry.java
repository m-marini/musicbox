/*
 * AbstractPaletteEntry.java
 *
 * $Id: AbstractPaletteEntry.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractPaletteEntry.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public abstract class AbstractPaletteEntry implements PaletteEntry {
	private AbstractButton button;

	/**
	 * @param sampleTransformerClass
	 */
	protected AbstractPaletteEntry() {
		init();
	}

	/**
	 * @param iconName
	 * @return
	 */
	private ImageIcon createImage(String iconName) {
		ImageIcon icon = null;
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(iconName);
		if (url == null)
			url = getClass().getResource(iconName);
		if (url != null)
			icon = new ImageIcon(url);
		return icon;
	}

	/**
	 * @see org.mmarini.sound.swing.PaletteEntry#getButton()
	 */
	@Override
	public AbstractButton getButton() {
		return button;
	}

	/**
         * 
         */
	private void init() {
		JToggleButton btn = new JToggleButton();
		ImageIcon icon = null;
		Class<? extends AbstractPaletteEntry> cl = getClass();
		String name = cl.getName();
		name = name.substring(cl.getPackage().getName().length() + 1);
		String iconName = Messages.getString(name + ".icon");
		if (!iconName.startsWith("!")) {
			icon = createImage(iconName);
		}
		if (icon != null) {
			btn.setIcon(icon);
		}
		String text = Messages.getString(name + ".text");
		btn.setToolTipText(text);
		if (icon == null) {
			btn.setText(text);
		}
		setButton(btn);
	}

	/**
	 * @param button
	 *            the button to set
	 */
	private void setButton(AbstractButton button) {
		this.button = button;
	}
}
