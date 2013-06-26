/*
 * Sequencer.java
 *
 * $Id: Sequencer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 13/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Sequencer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class Sequencer extends AbstractCompositeSampleTransformer {

	/**
         * 
         */
	public Sequencer() {
	}

	/**
	 * @param transformers
	 */
	public Sequencer(SampleTransformer[] transformers) {
		super(transformers);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		for (SampleTransformer tr : transformers) {
			tr.transform(value);
		}
	}
}
