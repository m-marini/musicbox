/*
 * GraphComponent.java
 *
 * $Id: GraphComponent.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: GraphComponent.java,v 1.1 2008/11/15 08:49:49 marco Exp $
 * 
 */
public class GraphComponent extends JComponent implements Constants {
	private static Logger log = LoggerFactory.getLogger(GraphComponent.class);

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private MusicBox musicBox;

	private NodeEvent nodeEvent = new NodeEvent(this);

	private MouseListener mouseListener = new MouseAdapter() {

		/**
		 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			handleMousePress(e);
		}
	};

	private NodeListener listener;

	/**
         * 
         */
	public GraphComponent() {
		init();
	}

	/**
	 * 
	 * @param listener
	 */
	public synchronized void addNodeListener(NodeListener listener) {
		this.listener = listener;
	}

	/**
         * 
         */
	private void fireNodeSelected() {
		if (listener != null) {
			listener.nodeSelected(nodeEvent);
		}
	}

	/**
	 * 
	 * @return
	 */
	public MusicBox getMusicBox() {
		return musicBox;
	}

	/**
	 * @param e
	 */
	private void handleMousePress(MouseEvent e) {
		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			Dimension size = getSize();
			MusicBox box = getMusicBox();
			int nc = box.getColumnCount();
			int nr = box.getRowCount();
			int sb = Math.min(size.width / nc, size.height / nr);
			int col = x / sb;
			int row = y / sb;
			if (col < nc && row < nr) {
				x = 2 * x - sb * (2 * col + 1);
				y = 2 * y - sb * (2 * row + 1);
				int direction;
				if (x < 0) {
					if (y < x) {
						direction = NORTH;
					} else if (y > -x) {
						direction = SOUTH;
					} else {
						direction = WEST;
					}
				} else if (x > 0) {
					if (y < -x) {
						direction = NORTH;
					} else if (y > x) {
						direction = SOUTH;
					} else {
						direction = EAST;
					}
				} else {
					if (y < 0) {
						direction = NORTH;
					} else {
						direction = SOUTH;
					}
				}
				nodeEvent.setDirection(direction);
				nodeEvent.setMusicBox(box);
				nodeEvent.setLocation(new Point(col, row));
				fireNodeSelected();
			}
		}
	}

	/**
         * 
         */
	private void init() {
		log.debug("init");
		addMouseListener(mouseListener);
	}

	/**
         * 
         */
	@Override
	protected void paintComponent(Graphics g) {
		paintMusicBox(g);
	}

	/**
	 * @param g
	 */
	private void paintMusicBox(Graphics g) {
		Dimension size = getSize();
		int w = size.width;
		int h = size.height;
		MusicBox box = getMusicBox();
		int nc = box.getColumnCount();
		int nr = box.getRowCount();
		int sb = Math.min(w / nc, h / nr);

		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				GraphNode graphNode = box.getNode(i, j);
				if (graphNode != null) {
					int x = j * sb;
					int y = i * sb;
					graphNode.paint((Graphics2D) g, x, y, sb, sb);
				}
			}
		}
	}

	/**
	 * 
	 * @param listener
	 */
	public synchronized void removeNodeListener(NodeListener listener) {
		if (this.listener == listener)
			this.listener = null;
	}

	/**
	 * @param graphNode
	 */
	public void select(GraphNode graphNode) {
		MusicBox box = getMusicBox();
		int nr = box.getRowCount();
		int nc = box.getColumnCount();
		for (int row = 0; row < nr; ++row) {
			for (int column = 0; column < nc; ++column) {
				GraphNode cell = box.getNode(row, column);
				if (cell != null) {
					if (cell.equals(graphNode)) {
						cell.setSelected(true);
					} else {
						cell.setSelected(false);
					}
				}
			}
		}
		repaint();
	}

	/**
	 * 
	 * @param musicBox
	 */
	public void setMusicBox(MusicBox musicBox) {
		this.musicBox = musicBox;
		repaint();
	}
}
