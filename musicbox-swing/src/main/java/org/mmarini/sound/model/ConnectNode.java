/*
 * ConnectGraphNode.java
 *
 * $Id: ConnectNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import org.mmarini.sound.transfomers.ConnectTransformer;
import org.mmarini.sound.transfomers.SampleTransformer;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ConnectNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class ConnectNode extends AbstractTransformNode {

	/**
	 * @param soundProcessor
	 */
	protected ConnectNode(SoundProcessor soundProcessor) {
		super(soundProcessor);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNode#apply(org.mmarini.sound.model.TransformNodeVisitor)
	 */
	@Override
	public void apply(TransformNodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNode#buildTransformer()
	 */
	@Override
	public SampleTransformer buildTransformer() {
		ConnectTransformer transformer = ConnectTransformer.getInstance();
		setSampleTransformer(transformer);
		return transformer;
	}
}
