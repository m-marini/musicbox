/*
 * SoundEvent.java
 *
 * $Id: SoundEvent.java,v 1.1 2008/11/20 21:07:26 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import java.util.EventObject;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SoundEvent.java,v 1.1 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class SoundEvent extends EventObject {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private TransformNode transformNode;

	/**
	 * @param source
	 */
	public SoundEvent(Object source) {
		super(source);
	}

	/**
	 * @return the transformNode
	 */
	public TransformNode getTransformNode() {
		return transformNode;
	}

	/**
	 * @param transformNode
	 *            the transformNode to set
	 */
	public void setTransformNode(TransformNode transformNode) {
		this.transformNode = transformNode;
	}

}
