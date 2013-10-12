/*
 * InputNode.java
 *
 * $Id: IconGraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: IconGraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class IconGraphNode extends AbstractGraphNode implements Constants {
	/**
         * 
         */
	private static final String OUTPUT_IMAGE = "output";

	/**
         * 
         */
	private static final String INPUT_IMAGE = "input";

	/**
         * 
         */
	private static final int ROUND_SIZE = 8;

	private static IconGraphNode inputNode;

	private static IconGraphNode outputNode;

	/**
	 * @param name
	 * @return
	 */
	protected static ImageIcon createImage(String name) {
		name = Messages.getString("IconGraphNode." + name + ".icon");
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(name);
		if (url == null)
			url = TransformGraphNode.class.getResource(name);
		ImageIcon icon = null;
		if (url != null)
			icon = new ImageIcon(url);
		return icon;
	}

	/**
	 * @return
	 */
	public static synchronized IconGraphNode createInputNode() {
		if (inputNode == null) {
			ImageIcon icon = createImage(INPUT_IMAGE);
			inputNode = new IconGraphNode(icon, false, true, false, false);
		}
		return inputNode;
	}

	/**
	 * @return
	 */
	public static synchronized IconGraphNode createOutputNode() {
		if (outputNode == null) {
			ImageIcon icon = createImage(OUTPUT_IMAGE);
			outputNode = new IconGraphNode(icon, false, false, false, true);
		}
		return outputNode;
	}

	private ImageIcon icon;

	private boolean westConnection;

	private boolean eastConnection;

	private boolean northConnection;

	private boolean southConnection;

	/**
         * 
         */
	public IconGraphNode() {
		this(null, false, false, false, false);
	}

	/**
	 * @param icon
	 * @param northConnection
	 * @param eastConnection
	 * @param southConnection
	 * @param westConnection
	 */
	public IconGraphNode(ImageIcon icon, boolean northConnection,
			boolean eastConnection, boolean southConnection,
			boolean westConnection) {
		this.icon = icon;
		this.westConnection = westConnection;
		this.eastConnection = eastConnection;
		this.northConnection = northConnection;
		this.southConnection = southConnection;
	}

	/**
	 * @return the icon
	 */
	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * @return the eastConnection
	 */
	public boolean isEastConnection() {
		return eastConnection;
	}

	/**
	 * @return the northConnection
	 */
	public boolean isNorthConnection() {
		return northConnection;
	}

	/**
	 * @return the southConnection
	 */
	public boolean isSouthConnection() {
		return southConnection;
	}

	/**
	 * @return the westConnection
	 */
	public boolean isWestConnection() {
		return westConnection;
	}

	/**
	 * @see org.mmarini.sound.swing.GraphNode#paint(java.awt.Graphics2D, int,
	 *      int, int, int)
	 */
	@Override
	public void paint(Graphics2D g, int x, int y, int w, int h) {
		if (isSelected()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRoundRect(x, y, w, h, ROUND_SIZE, ROUND_SIZE);
		}
		g.setColor(Color.BLACK);
		int iw = 0;
		int ih = 0;
		int ix;
		int iy;
		ImageIcon icon = getIcon();
		int hy = y + h / 2;
		int hx = x + w / 2;
		if (icon != null) {
			iw = icon.getIconWidth();
			ih = icon.getIconHeight();
			ix = x + (w - iw) / 2;
			iy = y + (h - ih) / 2;
			if (iw > w || ih > h) {
				g.drawImage(icon.getImage(), x, y, w, h,
						icon.getImageObserver());
				iw = w;
				ih = h;
			} else {
				g.drawImage(icon.getImage(), ix, y + (h - ih) / 2,
						icon.getImageObserver());
			}
		} else {
			ix = hx;
			iy = hy;
		}
		if (w > iw) {
			int ixx = x + (w + iw) / 2;
			if (isWestConnection())
				g.drawLine(x, hy, ix, hy);
			if (isEastConnection())
				g.drawLine(ixx, hy, x + w, hy);
		}
		if (h > ih) {
			int iyy = y + (h + ih) / 2;
			if (isNorthConnection())
				g.drawLine(hx, y, hx, iy);
			if (isSouthConnection())
				g.drawLine(hx, iyy, hx, y + h);
		}
		if (isSelected()) {
			g.drawRoundRect(x, y, w, h, ROUND_SIZE, ROUND_SIZE);
		}
	}

	/**
	 * @param eastConnection
	 *            the eastConnection to set
	 */
	public void setEastConnection(boolean rightConnection) {
		this.eastConnection = rightConnection;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	/**
	 * @param northConnection
	 *            the northConnection to set
	 */
	public void setNorthConnection(boolean northConnection) {
		this.northConnection = northConnection;
	}

	/**
	 * @param southConnection
	 *            the southConnection to set
	 */
	public void setSouthConnection(boolean southConnection) {
		this.southConnection = southConnection;
	}

	/**
	 * @param westConnection
	 *            the westConnection to set
	 */
	public void setWestConnection(boolean leftConnection) {
		this.westConnection = leftConnection;
	}
}
