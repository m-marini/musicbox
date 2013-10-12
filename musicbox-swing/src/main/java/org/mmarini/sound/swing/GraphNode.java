/*
 * GraphNode.java
 *
 * $Id: GraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Graphics2D;

import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: GraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public interface GraphNode {

	/**
	 * @return
	 */
	public abstract TransformNode getTransformNode();

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public abstract void paint(Graphics2D g, int x, int y, int w, int h);

	/**
	 * @param b
	 */
	public abstract void setSelected(boolean b);

}
