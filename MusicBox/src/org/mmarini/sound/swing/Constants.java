/*
 * Constants.java
 *
 * $Id: Constants.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 06/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: Constants.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public interface Constants {
	public static final int NORTH = 0;

	public static final int EAST = 1;

	public static final int SOUTH = 2;

	public static final int WEST = 3;

	/**
         * 
         */
	public static final ConnectGraphNode NES_NODE = new ConnectGraphNode(true,
			true, true, false);

	/**
         * 
         */
	public static final ConnectGraphNode NE_NODE = new ConnectGraphNode(true,
			true, false, false);

	public static final ConnectGraphNode NS_NODE = new ConnectGraphNode(true,
			false, true, false);

	/**
         * 
         */
	public static final ConnectGraphNode ESW_NODE = new ConnectGraphNode(false,
			true, true, true);

	/**
         * 
         */
	public static final ConnectGraphNode EW_NODE = new ConnectGraphNode(false,
			true, false, true);

	/**
         * 
         */
	public static final ConnectGraphNode NSW_NODE = new ConnectGraphNode(true,
			false, true, true);

	/**
         * 
         */
	public static final ConnectGraphNode NW_NODE = new ConnectGraphNode(true,
			false, false, true);

}
