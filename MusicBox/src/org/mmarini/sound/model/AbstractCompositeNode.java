/*
 * AbstractCompositeNode.java
 *
 * $Id: AbstractCompositeNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 12/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mmarini.sound.transfomers.SampleTransformer;
import org.w3c.dom.Element;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractCompositeNode.java,v 1.1 2008/11/15 08:49:49 marco Exp
 *          $
 * 
 */
public abstract class AbstractCompositeNode extends AbstractTransformNode {

	private List<TransformNode> childrenList;

	/**
         * 
         */
	protected AbstractCompositeNode(SoundProcessor soundProcessor) {
		super(soundProcessor);
		childrenList = new ArrayList<TransformNode>(0);
	}

	/**
	 * @param index
	 * @param node
	 */
	public void add(int index, TransformNode node) {
		getChildrenList().add(index, node);
		getSoundProcessor().handleStructureChanged(this);
	}

	/**
	 * @param node
	 */
	public void add(TransformNode node) {
		getChildrenList().add(node);
		getSoundProcessor().handleStructureChanged(this);
	}

	/**
	 * @param collection
	 */
	public void addAll(Collection<TransformNode> collection) {
		getChildrenList().addAll(collection);
		getSoundProcessor().handleStructureChanged(this);
	}

	/**
	 * @param context
	 * @return
	 */
	protected SampleTransformer[] createChildrenList() {
		List<TransformNode> children = getChildrenList();
		int n = children.size();
		SampleTransformer[] list = new SampleTransformer[n];
		for (int i = 0; i < n; ++i) {
			TransformNode child = children.get(i);
			list[i] = child.buildTransformer();
		}
		return list;
	}

	/**
	 * @see org.mmarini.sound.model.AbstractTransformNode#createElement(org.w3c.dom.Element)
	 */
	@Override
	public Element createElement(Element parent) {
		Element element = super.createElement(parent);
		for (TransformNode node : getChildrenList()) {
			node.createElement(element);
		}
		return element;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<TransformNode> getChildren() {
		return getChildrenList();
	}

	/**
	 * @param index
	 * @return
	 */
	public TransformNode getChildren(int index) {
		return getChildrenList().get(index);
	}

	/**
	 * @return
	 */
	public int getChildrenCount() {
		return getChildrenList().size();
	}

	/**
	 * @return the childrenList
	 */
	private List<TransformNode> getChildrenList() {
		return childrenList;
	}

	/**
	 * @param index
	 * @param node
	 */
	public void set(int index, TransformNode node) {
		getChildrenList().set(index, node);
		getSoundProcessor().handleStructureChanged(this);
	}
}
