/*
 * AbstractCompositeSampleTransformer.java
 *
 * $Id: AbstractCompositeSampleTransformer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 14/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractCompositeSampleTransformer.java,v 1.1 2008/11/15
 *          08:49:48 marco Exp $
 * 
 */
public abstract class AbstractCompositeSampleTransformer implements
		SampleTransformer {

	protected SampleTransformer[] transformers = new SampleTransformer[0];

	/**
         * 
         */
	protected AbstractCompositeSampleTransformer() {
	}

	/**
	 * @param transformers
	 */
	protected AbstractCompositeSampleTransformer(
			SampleTransformer[] transformers) {
		this.transformers = transformers;
	}

	/**
	 * @return the transformers
	 */
	public SampleTransformer[] getTransformers() {
		return transformers;
	}

	/**
	 * @param i
	 * @param insertNode
	 */
	public void insertAt(int i, SampleTransformer insertNode) {
		SampleTransformer[] list = getTransformers();
		int n = list.length;
		int m = n + 1;
		SampleTransformer[] newList = new SampleTransformer[m];
		newList[i] = insertNode;
		if (i > 0) {
			System.arraycopy(list, 0, newList, 0, i);
		}
		if (i < n) {
			System.arraycopy(list, i, newList, i + 1, n - i);
		}
		setTransformers(newList);
	}

	/**
	 * @param transformers
	 *            the transformers to set
	 */
	public void setTransformers(SampleTransformer[] tranformers) {
		this.transformers = tranformers;
	}

}