/*
 * MusicBox.java
 *
 * $Id: MusicBox.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.mmarini.sound.model.SoundEvent;
import org.mmarini.sound.model.SoundListener;
import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: MusicBox.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class MusicBox {
	private List<List<GraphNode>> graphNodes;

	private LocateInserter inserter;

	private SoundProcessor soundProcessor;

	private SoundListener listener;

	/**
         * 
         */
	public MusicBox() {
		inserter = new LocateInserter();
		graphNodes = new ArrayList<List<GraphNode>>(0);
		listener = new SoundListener() {

			@Override
			public void nodeChanged(SoundEvent event) {
			}

			@Override
			public void rootChanged(SoundEvent event) {
				rebuild();
			}

			@Override
			public void structureChanged(SoundEvent event) {
				rebuild();
			}

		};
	}

	/**
	 * 
	 * @param transformNode
	 */
	public void apply(TransformNode transformNode) {
	}

	/**
         * 
         * 
         */
	public void clear() {
		getNodes().clear();
	}

	/**
	 * @return
	 */
	public int getColumnCount() {
		List<List<GraphNode>> graphNodes = getNodes();
		if (graphNodes.isEmpty())
			return 0;
		return graphNodes.get(0).size();
	}

	/**
	 * @param row
	 * @param column
	 * @return
	 */
	public GraphNode getNode(int row, int column) {
		return getNodes().get(row).get(column);
	}

	/**
	 * @param location
	 * @return
	 */
	public GraphNode getNode(Point location) {
		return getNode(location.y, location.x);
	}

	/**
	 * 
	 * @return
	 */
	private List<List<GraphNode>> getNodes() {
		return graphNodes;
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return getNodes().size();
	}

	/**
	 * @return the soundProcessor
	 */
	private SoundProcessor getSoundProcessor() {
		return soundProcessor;
	}

	/**
	 * @param location
	 * @param direction
	 * @param trans
	 */
	public void insert(Point location, int direction, TransformNode trans) {
		GraphNode graphNode = getNode(location);
		if (graphNode != null) {
			TransformNode tr = graphNode.getTransformNode();
			if (tr != null) {
				inserter.insert(getSoundProcessor().getTransformNode(), tr,
						direction, trans);
			}
		}
	}

	/**
	 * @param trans
	 */
	public Point locate(TransformNode trans) {
		int nr = getRowCount();
		int nc = getColumnCount();
		for (int row = 0; row < nr; ++row) {
			for (int col = 1; col < nc - 1; ++col) {
				GraphNode graphNode = getNode(row, col);
				if (graphNode != null
						&& trans.equals(graphNode.getTransformNode())) {
					return new Point(col, row);
				}
			}
		}
		return null;
	}

	/**
         * 
         */
	protected void rebuild() {
		MusicBoxBuilder builder = new MusicBoxBuilder();
		builder.setMusicBox(this);
		builder.build(getSoundProcessor().getTransformNode());
	}

	/**
	 * @param row
	 * @param column
	 */
	private void resize(int row, int column) {
		List<List<GraphNode>> graphNodes = getNodes();
		int nc = getColumnCount();
		int nr = getRowCount();
		if (column >= nc) {
			for (int k = 0; k < nr; ++k) {
				List<GraphNode> rowv = graphNodes.get(k);
				for (int l = nc; l <= column; ++l)
					rowv.add(null);
			}
			nc = column + 1;
		}
		for (int k = nr; k <= row; ++k) {
			ArrayList<GraphNode> rowv = new ArrayList<GraphNode>(nc);
			for (int l = 0; l < nc; ++l)
				rowv.add(null);
			graphNodes.add(rowv);
		}
	}

	/**
	 * @param row
	 * @param colum
	 * @param graphNode
	 */
	public void setNode(int row, int colum, GraphNode graphNode) {
		resize(row, colum);
		getNodes().get(row).set(colum, graphNode);
	}

	/**
	 * @param location
	 * @param graphNode
	 */
	public void setNode(Point location, GraphNode graphNode) {
		setNode(location.y, location.x, graphNode);
	}

	/**
	 * @param soundProcessor
	 *            the soundProcessor to set
	 */
	public void setSoundProcessor(SoundProcessor soundProcessor) {
		if (this.soundProcessor != null)
			this.soundProcessor.removeSoundListener(listener);
		this.soundProcessor = soundProcessor;
		if (soundProcessor != null)
			soundProcessor.addSoundListener(listener);
	}
}
