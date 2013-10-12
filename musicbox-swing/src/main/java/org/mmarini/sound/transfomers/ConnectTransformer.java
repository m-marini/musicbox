/*
 * ConnectTransformer.java
 *
 * $Id: ConnectTransformer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

import java.io.Serializable;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ConnectTransformer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class ConnectTransformer implements SampleTransformer, Serializable {
	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private static ConnectTransformer instance = new ConnectTransformer();

	/**
	 * 
	 * @return
	 */
	public static ConnectTransformer getInstance() {
		return instance;
	}

	/**
         * 
         */
	protected ConnectTransformer() {
	}

	/**
	 * @see org.mmarini.sound.transfomers.SampleTransformer#transform(int[])
	 */
	@Override
	public void transform(int[] value) {
	}

}
