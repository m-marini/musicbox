/*
 * SampleTransformerVisitorAdapter.java
 *
 * $Id: TransformNodeVisitorAdapter.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: TransformNodeVisitorAdapter.java,v 1.1 2008/11/15 08:49:49
 *          marco Exp $
 * 
 */
public class TransformNodeVisitorAdapter implements TransformNodeVisitor {

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.MixerNode)
	 */
	@Override
	public void visit(AbstractCompositeNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.AutoMuteNode)
	 */
	@Override
	public void visit(AutoMuteNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.ConnectNode)
	 */
	@Override
	public void visit(ConnectNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.EchoNode)
	 */
	@Override
	public void visit(EchoNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.LevelMeterNode)
	 */
	@Override
	public void visit(LevelMeterNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.ModulateNode)
	 */
	@Override
	public void visit(ModulateNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.SequenceNode)
	 */
	@Override
	public void visit(SequenceNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.ToneShiftNode)
	 */
	@Override
	public void visit(ToneShiftNode node) {
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.VolumeNode)
	 */
	@Override
	public void visit(VolumeNode node) {
	}
}
