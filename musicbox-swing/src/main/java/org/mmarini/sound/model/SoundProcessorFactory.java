/*
 * Processor.java
 *
 * $Id: SoundProcessorFactory.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import javax.sound.sampled.AudioFormat;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SoundProcessorFactory.java,v 1.1 2008/11/15 08:49:49 marco Exp
 *          $
 * 
 */
public class SoundProcessorFactory {
	private static SoundProcessorFactory instance = new SoundProcessorFactory();

	/**
	 * @return the instance
	 */
	public static SoundProcessorFactory getInstance() {
		return instance;
	}

	/**
         * 
         */
	protected SoundProcessorFactory() {
	}

	/**
	 * 
	 * @return
	 */
	public SoundProcessor create(AudioFormat audioFormat) {
		return new SoundProcessorImpl(audioFormat);
	}
}
