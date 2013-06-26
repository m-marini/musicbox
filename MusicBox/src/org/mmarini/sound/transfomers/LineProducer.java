/*
 * LineProducer.java
 *
 * $Id: LineProducer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LineProducer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class LineProducer implements SampleProducer {
	private TargetDataLine line;

	/**
         * 
         */
	public LineProducer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param line
	 */
	public LineProducer(TargetDataLine line) {
		super();
		this.line = line;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#available()
	 */
	@Override
	public int available() {
		return line.available();
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#getBufferSize()
	 */
	@Override
	public int getBufferSize() {
		return line.getBufferSize();
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#getFormat()
	 */
	@Override
	public AudioFormat getFormat() {
		return line.getFormat();
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] bfr, int off, int size) {
		return line.read(bfr, off, size);
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(TargetDataLine line) {
		this.line = line;
	}

}
