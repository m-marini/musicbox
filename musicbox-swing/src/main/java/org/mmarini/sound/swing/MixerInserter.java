/*
 * MixerParentInserter.java
 *
 * $Id: MixerInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 08/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.TransformNode;

/**
 * The class inserts an object into a mixer parent
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: MixerInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class MixerInserter extends AbstractParentInserter implements Constants {
	/**
	 * @param node
	 */
	private void insertCompositeAfter(TransformNode node) {
		AbstractCompositeNode parent = getParent();
		SequenceNode comp = parent.getSoundProcessor().createSequenceNode();
		int i = getIndex();
		comp.add(parent.getChildren(i));
		comp.add(getNode());
		parent.set(i, comp);
	}

	/**
	 * @param node
	 */
	private void insertCompositeBefore(TransformNode node) {
		AbstractCompositeNode parent = getParent();
		SequenceNode comp = parent.getSoundProcessor().createSequenceNode();
		int i = getIndex();
		comp.add(getNode());
		comp.add(parent.getChildren(i));
		parent.set(i, comp);
	}

	/**
	 * @param node
	 */
	@Override
	protected void insertOther(TransformNode node) {
		switch (getDirection()) {
		case WEST:
			insertCompositeBefore(node);
			break;
		case EAST:
			insertCompositeAfter(node);
			break;
		}
	}

	/**
         * 
         */
	@Override
	public void visit(AbstractCompositeNode transformer) {
		switch (getDirection()) {
		case SOUTH:
		case WEST:
			insertFirst(transformer);
			break;
		case EAST:
			insertCompositeAfter(transformer);
			break;
		}
	}
}
