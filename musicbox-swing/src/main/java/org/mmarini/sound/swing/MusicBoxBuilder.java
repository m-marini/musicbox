/*
 * MusicBoxBuilder.java
 *
 * $Id: MusicBoxBuilder.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 02/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Dimension;
import java.awt.Point;

import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: MusicBoxBuilder.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class MusicBoxBuilder {
	public MusicBox musicBox;

	/**
         * 
         * 
         */
	public void build(TransformNode transformer) {
		MusicBox box = getMusicBox();
		box.clear();
		IconGraphNode input = IconGraphNode.createInputNode();
		box.setNode(0, 0, input);
		MusicBoxBuilderContext ctx = new MusicBoxBuilderContext();
		ctx.setMusicBox(box);
		ctx.setLocation(new Point(1, 0));
		transformer.apply(ctx);
		Dimension size = ctx.getSize();
		IconGraphNode output = IconGraphNode.createOutputNode();
		box.setNode(0, size.width + 1, output);
	}

	/**
	 * 
	 * @return
	 */
	public MusicBox getMusicBox() {
		return musicBox;
	}

	/**
	 * 
	 * @param musicBox
	 */
	public void setMusicBox(MusicBox musicBox) {
		this.musicBox = musicBox;
	}
}
