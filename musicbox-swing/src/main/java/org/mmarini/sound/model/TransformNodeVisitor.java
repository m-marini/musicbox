/*
 * TransformNodeVisitor.java
 *
 * $Id: TransformNodeVisitor.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: TransformNodeVisitor.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public interface TransformNodeVisitor {

	/**
	 * @param node
	 */
	public abstract void visit(AbstractCompositeNode node);

	/**
	 * @param node
	 */
	public abstract void visit(AutoMuteNode node);

	/**
	 * @param node
	 */
	public abstract void visit(ConnectNode node);

	/**
	 * @param node
	 */
	public abstract void visit(EchoNode node);

	/**
	 * @param node
	 */
	public abstract void visit(LevelMeterNode node);

	/**
	 * @param node
	 */
	public abstract void visit(ModulateNode node);

	/**
	 * @param node
	 */
	public abstract void visit(SequenceNode node);

	/**
	 * @param node
	 */
	public abstract void visit(ToneShiftNode node);

	/**
	 * @param node
	 */
	public abstract void visit(VolumeNode node);
}
