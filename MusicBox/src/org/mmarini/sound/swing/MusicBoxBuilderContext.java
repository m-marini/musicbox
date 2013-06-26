/*
 * MusicBoxBuilderContext.java
 *
 * $Id: MusicBoxBuilderContext.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Dimension;
import java.awt.Point;

import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.AutoMuteNode;
import org.mmarini.sound.model.EchoNode;
import org.mmarini.sound.model.LevelMeterNode;
import org.mmarini.sound.model.ModulateNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.ToneShiftNode;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.model.TransformNodeVisitor;
import org.mmarini.sound.model.VolumeNode;
import org.mmarini.sound.transfomers.Sequencer;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: MusicBoxBuilderContext.java,v 1.1 2008/11/15 08:49:48 marco Exp
 *          $
 * 
 */
public class MusicBoxBuilderContext implements TransformNodeVisitor, Constants {

	private MusicBox musicBox;

	private Point location;

	private Dimension size;

	/**
	 * @param module
	 */
	private void generateModule(IconGraphNode module) {
		getMusicBox().setNode(getLocation(), module);
		setSize(new Dimension(1, 1));
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the musicBox
	 */
	public MusicBox getMusicBox() {
		return musicBox;
	}

	/**
	 * @return the size
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * @param musicBox
	 *            the musicBox to set
	 */
	public void setMusicBox(MusicBox musicBox) {
		this.musicBox = musicBox;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Mixer)
	 */
	@Override
	public void visit(AbstractCompositeNode node) {
		int w = 0;
		int h = 0;
		Point loc = getLocation();
		int x = loc.x;
		int y = loc.y;
		int n = node.getChildrenCount();
		Dimension[] sizes = new Dimension[n];
		for (int i = 0; i < n; ++i) {
			loc.x = x + 1;
			loc.y = y + h;
			TransformNode tran = node.getChildren(i);
			tran.apply(this);
			Dimension size = getSize();
			sizes[i] = new Dimension(size);
			w = Math.max(w, size.width);
			h += size.height;
		}
		MusicBox mb = getMusicBox();
		IconGraphNode module = TransformGraphNode.create(node);
		if (n > 1)
			mb.setNode(y, x, ESW_NODE);
		else
			mb.setNode(y, x, EW_NODE);
		mb.setNode(y, x + w + 1, module);
		int yy = y;
		for (int i = 1; i < n; ++i) {
			Dimension size = sizes[i - 1];
			for (int j = 1; j < size.height; ++j) {
				mb.setNode(yy + j, x, NS_NODE);
				mb.setNode(yy + j, x + w + 1, NS_NODE);
			}
			yy += size.height;
			if (i == n - 1) {
				mb.setNode(yy, x, NE_NODE);
				mb.setNode(yy, x + w + 1, NW_NODE);
			} else {
				mb.setNode(yy, x, NES_NODE);
				mb.setNode(yy, x + w + 1, NSW_NODE);
			}
		}
		yy = y;
		for (int i = 0; i < n; ++i) {
			Dimension size = sizes[i];
			for (int j = size.width; j < w; ++j) {
				mb.setNode(yy, x + j + 1, EW_NODE);
			}
			yy += size.height;
		}
		setSize(new Dimension(w + 2, Math.max(h, 1)));
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.model.AutoMuteNode)
	 */
	@Override
	public void visit(AutoMuteNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Echo)
	 */
	@Override
	public void visit(EchoNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.LevelMeter)
	 */
	@Override
	public void visit(LevelMeterNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.Modulator)
	 */
	@Override
	public void visit(ModulateNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ConnectTransformer)
	 */
	@Override
	public void visit(org.mmarini.sound.model.ConnectNode transformer) {
		IconGraphNode module = TransformGraphNode.create(transformer);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(Sequencer)
	 */
	@Override
	public void visit(SequenceNode node) {
		int w = 0;
		int h = 0;
		Point location = getLocation();
		int x = location.x;
		int y = location.y;
		for (TransformNode tran : node.getChildren()) {
			location.x = x;
			location.y = y;
			tran.apply(this);
			Dimension size = getSize();
			x += size.width;
			w += size.width;
			h = Math.max(h, size.height);
		}
		setSize(new Dimension(w, h));
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.ToneShifter)
	 */
	@Override
	public void visit(ToneShiftNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}

	/**
	 * @see org.mmarini.sound.model.TransformNodeVisitor#visit(org.mmarini.sound.transfomers.VolumeControl)
	 */
	@Override
	public void visit(VolumeNode node) {
		IconGraphNode module = TransformGraphNode.create(node);
		generateModule(module);
	}
}
