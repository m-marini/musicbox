/*
 * Sequencer.java
 *
 * $Id: Mixer.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 13/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Mixer.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class Mixer extends AbstractCompositeSampleTransformer {
	/**
         * 
         */
	public Mixer() {
	}

	/**
	 * @param transformers
	 */
	public Mixer(SampleTransformer[] transformers) {
		super(transformers);
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
		int n = transformers.length;
		if (n > 0) {
			int[] val = new int[2];
			int[] result = new int[2];
			for (SampleTransformer tr : transformers) {
				val[0] = value[0];
				val[1] = value[1];
				tr.transform(value);
				result[0] += val[0];
				result[1] += val[1];
			}
			result[0] /= n;
			result[1] /= n;
		}
	}

}
