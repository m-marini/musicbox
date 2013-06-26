/*
 * ParentInserter.java
 *
 * $Id: LocateInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 06/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.model.TransformNodeVisitorAdapter;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LocateInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class LocateInserter extends TransformNodeVisitorAdapter implements
		Constants {
	private TransformNode target;

	private MixerInserter mixerInserter = new MixerInserter();

	private CompositeInserter compositeInserter = new CompositeInserter();

	private boolean found;

	private TransformNode node;

	private int direction;

	/**
	 * @param musicBox
	 */
	public LocateInserter() {
	}

	/**
	 * @param parent
	 */
	private void findDeeper(AbstractCompositeNode parent,
			AbstractParentInserter inserter) {
		int n = parent.getChildrenCount();
		for (int i = 0; !isFound() && i < n; ++i) {
			TransformNode tr = parent.getChildren(i);
			TransformNode ref = getTarget();
			if (tr == ref) {
				inserter.insert(parent, i, getDirection(), getNode());
				setFound(true);
				break;
			} else {
				tr.apply(this);
			}
		}
	}

	/**
	 * @return the direction
	 */
	private int getDirection() {
		return direction;
	}

	/**
	 * @return the node
	 */
	private TransformNode getNode() {
		return node;
	}

	/**
	 * @return the target
	 */
	private TransformNode getTarget() {
		return target;
	}

	/**
	 * 
	 * @param root
	 * @param target
	 * @param direction
	 * @param node
	 */
	public void insert(TransformNode root, TransformNode target, int direction,
			TransformNode node) {
		setDirection(direction);
		setFound(false);
		setNode(node);
		setTarget(target);
		root.apply(this);
	}

	/**
	 * @return the found
	 */
	private boolean isFound() {
		return found;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	private void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	private void setFound(boolean found) {
		this.found = found;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	private void setNode(TransformNode node) {
		this.node = node;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	private void setTarget(TransformNode target) {
		this.target = target;
	}

	/**
         * 
         */
	@Override
	public void visit(AbstractCompositeNode transformer) {
		findDeeper(transformer, mixerInserter);
	}

	/**
         * 
         */
	@Override
	public void visit(SequenceNode transformer) {
		findDeeper(transformer, compositeInserter);
	}

}
