/*
 * SampleProcessor.java
 *
 * $Id: SampleProcessor.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 13/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SampleProcessor.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class SampleProcessor {
	private SampleConsumer consumer;

	private SampleProducer producer;

	private int outputSize;

	private byte[] outputBuffer;

	private int outputFrameSize;

	private int inputSize;

	private int inputIdx;

	private byte[] inputBuffer;

	private int inputFrameSize;

	private boolean outputBigEndian;

	private boolean inputBigEndian;

	private int inputOffset;

	private int[] value = new int[2];

	private SampleTransformerKeeper keeper;

	private Thread processThread;

	/**
         * 
         */
	public SampleProcessor() {
	}

	/**
	 * @param consumer
	 * @param producer
	 */
	public SampleProcessor(SampleConsumer source, SampleProducer target) {
		this.consumer = source;
		this.producer = target;
	}

	/**
         * 
         */
	private void flush() {
		consumer.write(outputBuffer, 0, outputSize);
		outputSize = 0;
	}

	/**
         * 
         * 
         */
	public void init() {
		AudioFormat consumerFormat = consumer.getFormat();
		AudioFormat producerFormat = producer.getFormat();

		if (consumerFormat.getFrameRate() != producerFormat.getFrameRate())
			throw new Error("Bit rate does not match");

		if (!consumerFormat.getEncoding().equals(
				AudioFormat.Encoding.PCM_SIGNED))
			throw new Error("Consumer encoding is not PCM_SIGNED");

		Encoding encoding = producerFormat.getEncoding();
		if (!(encoding.equals(AudioFormat.Encoding.PCM_SIGNED) || encoding
				.equals(AudioFormat.Encoding.PCM_UNSIGNED)))
			throw new Error("Producer encoding is not PCM");

		outputBigEndian = consumerFormat.isBigEndian();
		outputFrameSize = consumerFormat.getFrameSize();
		int bfrSize = consumer.getBufferSize();
		outputBuffer = new byte[bfrSize];
		outputSize = 0;

		inputBigEndian = producerFormat.isBigEndian();
		if (encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
			inputOffset = 1 << (producerFormat.getSampleSizeInBits() - 1);
		} else {
			inputOffset = 0;
		}
		inputFrameSize = producerFormat.getFrameSize();
		bfrSize = producer.getBufferSize();
		inputBuffer = new byte[bfrSize];
		inputIdx = 0;
		inputSize = 0;
	}

	/**
         * 
         */
	protected void process() {
		Thread th = Thread.currentThread();
		while (processThread == th) {
			readValue(value);
			SampleTransformer sampleTransformer = keeper.getSampleTransformer();
			if (sampleTransformer != null)
				sampleTransformer.transform(value);
			writeValue(value);
		}
	}

	/**
	 * @return
	 */
	private void readValue(int[] value) {
		if (inputIdx >= inputSize) {
			flush();
			inputSize = Math.max(producer.available(), inputFrameSize);
			while ((inputSize = producer.read(inputBuffer, 0, inputSize)) == 0)
				;
			inputIdx = 0;
		}
		int n = inputIdx + inputFrameSize;
		int v;
		if (inputBigEndian) {
			/*
			 * Big endian
			 */
			v = inputBuffer[inputIdx];
			for (int i = inputIdx + 1; i < n; ++i) {
				v <<= 8;
				v |= (inputBuffer[i] & 0xff);
			}
		} else {
			/*
			 * Little endian
			 */
			v = inputBuffer[n - 1];
			for (int i = n - 2; i >= inputIdx; --i) {
				v <<= 8;
				v |= (inputBuffer[i] & 0xff);
			}
		}
		inputIdx = n;
		value[0] = v - inputOffset;
		value[1] = v - inputOffset;
	}

	/**
	 * @param consumer
	 *            the consumer to set
	 */
	public void setConsumer(SampleConsumer source) {
		this.consumer = source;
	}

	/**
	 * @param keeper
	 *            the keeper to set
	 */
	public void setKeeper(SampleTransformerKeeper keeper) {
		this.keeper = keeper;
	}

	/**
	 * @param producer
	 *            the producer to set
	 */
	public void setProducer(SampleProducer target) {
		this.producer = target;
	}

	/**
         * 
         * 
         */
	public synchronized void start() {
		processThread = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
		}, getClass().getName());
		processThread.start();
	}

	public synchronized void stop() {
		processThread = null;
	}

	/**
	 * 
	 * @param value
	 */
	private void writeValue(int[] value) {
		if (outputSize >= outputBuffer.length) {
			flush();
		}
		if (outputBigEndian) {
			/*
			 * Big endian
			 */
			for (int i = outputSize + outputFrameSize - 1; i >= outputSize; --i) {
				outputBuffer[i] = (byte) value[0];
				value[0] >>= 8;
			}
			outputSize += outputFrameSize;
		} else {
			/*
			 * Little endian
			 */
			for (int i = 0; i < outputFrameSize; ++i) {
				outputBuffer[outputSize++] = (byte) value[0];
				value[0] >>= 8;
			}
		}
	}
}
