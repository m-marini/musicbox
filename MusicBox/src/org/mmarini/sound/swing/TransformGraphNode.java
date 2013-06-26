/*
 * InputNode.java
 *
 * $Id: TransformGraphNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: TransformGraphNode.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class TransformGraphNode extends IconGraphNode implements Constants {
	private static Map<Class<?>, TransformGraphNode> prototypes = new HashMap<Class<?>, TransformGraphNode>();

	/**
	 * 
	 * @param transformNode
	 * @return
	 */
	public static IconGraphNode create(AbstractCompositeNode transformer) {
		TransformGraphNode prot;
		Class<?> trClass = transformer.getClass();
		synchronized (prototypes) {
			prot = prototypes.get(trClass);
			if (prot == null) {
				prot = createPrototype(trClass);
				prot.setSouthConnection(true);
				prototypes.put(trClass, prot);
			}
		}
		TransformGraphNode node = new TransformGraphNode(prot);
		node.setTransformNode(transformer);
		return node;
	}

	/**
	 * 
	 * @param transformNode
	 * @return
	 */
	public static IconGraphNode create(TransformNode transformNode) {
		TransformGraphNode prot = getGenericPrototype(transformNode.getClass());
		TransformGraphNode node = new TransformGraphNode(prot);
		node.setTransformNode(transformNode);
		return node;
	}

	/**
	 * @param trClass
	 * @return
	 */
	private static TransformGraphNode createPrototype(Class<?> trClass) {
		TransformGraphNode prot;
		ImageIcon icon = createImage(trClass.getName());
		prot = new TransformGraphNode(icon, false, true, false, true, null);
		return prot;
	}

	/**
	 * 
	 * @param trClass
	 * @return
	 */
	private static TransformGraphNode getGenericPrototype(Class<?> trClass) {
		synchronized (prototypes) {
			TransformGraphNode prot = prototypes.get(trClass);
			if (prot == null) {
				prot = createPrototype(trClass);
				prototypes.put(trClass, prot);
			}
			return prot;
		}
	}

	private TransformNode transformNode;

	/**
         * 
         */
	public TransformGraphNode() {
		this(null, false, false, false, false, null);
	}

	/**
	 * 
	 * @param node
	 */
	public TransformGraphNode(IconGraphNode node) {
		this(node.getIcon(), node.isNorthConnection(), node.isEastConnection(),
				node.isSouthConnection(), node.isWestConnection(), node
						.getTransformNode());
	}

	/**
	 * 
	 * @param icon
	 * @param northConnection
	 * @param eastConnection
	 * @param southConnection
	 * @param westConnection
	 * @param node
	 */
	public TransformGraphNode(ImageIcon icon, boolean northConnection,
			boolean eastConnection, boolean southConnection,
			boolean westConnection, TransformNode node) {
		super(icon, northConnection, eastConnection, southConnection,
				westConnection);
		setTransformNode(node);
	}

	/**
	 * @return the transformNode
	 */
	@Override
	public TransformNode getTransformNode() {
		return transformNode;
	}

	/**
	 * @param node
	 *            the transformNode to set
	 */
	public void setTransformNode(TransformNode node) {
		this.transformNode = node;
	}
}
