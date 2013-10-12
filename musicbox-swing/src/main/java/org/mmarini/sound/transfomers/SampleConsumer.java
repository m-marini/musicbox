/*
 * SampleConsumer.java
 *
 * $Id: SampleConsumer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import javax.sound.sampled.AudioFormat;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SampleConsumer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public interface SampleConsumer {

	/**
	 * @return
	 */
	public abstract int getBufferSize();

	/**
	 * @return
	 */
	public abstract AudioFormat getFormat();

	/**
	 * @param bfr
	 * @param i
	 * @param size
	 */
	public abstract int write(byte[] bfr, int i, int size);

}
