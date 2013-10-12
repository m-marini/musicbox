/*
 * StreamProducer.java
 *
 * $Id: StreamProducer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: StreamProducer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class StreamProducer implements SampleProducer {
	/**
         * 
         */
	private static final int BUFFER_SIZE = 8000;

	private AudioInputStream stream;

	/**
         * 
         */
	public StreamProducer() {
	}

	/**
	 * @param stream
	 */
	public StreamProducer(AudioInputStream stream) {
		super();
		this.stream = stream;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#available()
	 */
	@Override
	public int available() {
		try {
			int size = Math.min(stream.available(), BUFFER_SIZE);
			return size;
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#getBufferSize()
	 */
	@Override
	public int getBufferSize() {
		return BUFFER_SIZE;
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#getFormat()
	 */
	@Override
	public AudioFormat getFormat() {
		return stream.getFormat();
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleProducer#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] bfr, int off, int size) {
		try {
			return stream.read(bfr, off, size);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	/**
	 * @param stream
	 *            the stream to set
	 */
	public void setStream(AudioInputStream stream) {
		this.stream = stream;
	}

}
