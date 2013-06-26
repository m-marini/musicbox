/*
 * SampleTransformer.java
 *
 * $Id: SampleTransformer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 13/ott/08
 *
 * Copyright notice
 */
package org.mmarini.sound.transfomers;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SampleTransformer.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public interface SampleTransformer {
	/**
	 * 
	 * @param value
	 * @return
	 */
	public void transform(int[] value);
}
