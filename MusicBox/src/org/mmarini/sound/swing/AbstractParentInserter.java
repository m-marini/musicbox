/*
 * AbstractParentInserter.java
 *
 * $Id: AbstractParentInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 08/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.AutoMuteNode;
import org.mmarini.sound.model.ConnectNode;
import org.mmarini.sound.model.EchoNode;
import org.mmarini.sound.model.LevelMeterNode;
import org.mmarini.sound.model.ModulateNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.ToneShiftNode;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.model.TransformNodeVisitor;
import org.mmarini.sound.model.VolumeNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractParentInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp
 *          $
 * 
 */
public abstract class AbstractParentInserter implements TransformNodeVisitor {
	private AbstractCompositeNode parent;

	private TransformNode node;

	private int index;

	private int direction;

	/**
	 * @return the direction
	 */
	protected int getDirection() {
		return direction;
	}

	/**
	 * @return the index
	 */
	protected int getIndex() {
		return index;
	}

	/**
	 * @return the node
	 */
	protected TransformNode getNode() {
		return node;
	}

	/**
	 * @return the parent
	 */
	protected AbstractCompositeNode getParent() {
		return parent;
	}

	/**
	 * 
	 * @param parent
	 * @param index
	 * @param direction
	 * @param node
	 */
	public void insert(AbstractCompositeNode parent, int index, int direction,
			TransformNode node) {
		setParent(parent);
		setIndex(index);
		setDirection(direction);
		setNode(node);
		parent.getChildren(index).apply(this);
	}

	/**
         * 
         */
	protected void insertFirst(AbstractCompositeNode mixer) {
		mixer.add(0, getNode());
	}

	/**
	 * @param node
	 */
	protected abstract void insertOther(TransformNode node);

	/**
	 * @param direction
	 *            the direction to set
	 */
	protected void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	protected void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	protected void setNode(TransformNode node) {
		this.node = node;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	protected void setParent(AbstractCompositeNode parent) {
		this.parent = parent;
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.AutoMuteNode)
	 */
	@Override
	public void visit(AutoMuteNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ConnectTransformer)
	 */
	@Override
	public void visit(ConnectNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Echo)
	 */
	@Override
	public void visit(EchoNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.LevelMeter)
	 */
	@Override
	public void visit(LevelMeterNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Modulator)
	 */
	@Override
	public void visit(ModulateNode node) {
		insertOther(node);
	}

	/**
         * 
         */
	@Override
	public void visit(SequenceNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ToneShifter)
	 */
	@Override
	public void visit(ToneShiftNode node) {
		insertOther(node);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.VolumeControl)
	 */
	@Override
	public void visit(VolumeNode node) {
		insertOther(node);
	}
}
