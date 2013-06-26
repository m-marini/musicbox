/*
 * Messages.java
 *
 * $Id: Messages.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 06/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Messages.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class Messages {
	private static final String BUNDLE_NAME = "org.mmarini.sound.swing.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	private Messages() {
	}
}
