/*
 * MixerParentInserter.java
 *
 * $Id: CompositeInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 08/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.TransformNode;

/**
 * The class inserts an object into a composite parent
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: CompositeInserter.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class CompositeInserter extends AbstractParentInserter implements
		Constants {

	/**
         * 
         */
	private void insertAfter() {
		getParent().add(getIndex() + 1, getNode());
	}

	/**
	 * @param transformer
	 */
	private void insertBefore() {
		getParent().add(getIndex(), getNode());
	}

	/**
	 * @param transformer
	 */
	@Override
	protected void insertOther(TransformNode transformer) {
		switch (getDirection()) {
		case WEST:
			insertBefore();
			break;
		case EAST:
			insertAfter();
			break;
		}
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Mixer)
	 */
	@Override
	public void visit(AbstractCompositeNode node) {
		switch (getDirection()) {
		case SOUTH:
		case WEST:
			insertFirst(node);
			break;
		case EAST:
			insertAfter();
			break;
		}
	}
}
