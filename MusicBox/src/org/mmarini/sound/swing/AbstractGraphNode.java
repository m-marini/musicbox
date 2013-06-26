/*
 * AbstractGraphNode.java
 *
 * $Id: AbstractGraphNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractGraphNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public abstract class AbstractGraphNode implements GraphNode {
	private boolean selected;

	/**
         * 
         */
	protected AbstractGraphNode() {
	}

	/**
	 * @see org.mmarini.sound.swing.GraphNode#getTransformNode()
	 */
	@Override
	public TransformNode getTransformNode() {
		return null;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
