/*
 * LineConsumer.java
 *
 * $Id: LineConsumer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LineConsumer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class LineConsumer implements SampleConsumer {
	private SourceDataLine line;

	/**
         * 
         */
	public LineConsumer() {
	}

	/**
	 * @param line
	 */
	public LineConsumer(SourceDataLine line) {
		super();
		this.line = line;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleConsumer#getBufferSize()
	 */
	@Override
	public int getBufferSize() {
		return line.getBufferSize();
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleConsumer#getFormat()
	 */
	@Override
	public AudioFormat getFormat() {
		return line.getFormat();
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(SourceDataLine line) {
		this.line = line;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleConsumer#write(byte[], int, int)
	 */
	@Override
	public int write(byte[] bfr, int off, int size) {
		return line.write(bfr, off, size);
	}

}
