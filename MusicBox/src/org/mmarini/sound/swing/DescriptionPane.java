/*
 * VolumePane.java
 *
 * $Id: DescriptionPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 11/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: DescriptionPane.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class DescriptionPane extends JPanel {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
         * 
         * 
         */
	public DescriptionPane() {
		setDescription("Description");
	}

	/**
	 * 
	 * @param type
	 */
	public void setDescription(String description) {
		setBorder(BorderFactory.createTitledBorder(description));
	}
}
