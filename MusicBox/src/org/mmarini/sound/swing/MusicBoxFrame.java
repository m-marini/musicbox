/*
 * MusicBoxFrame.java
 *
 * $Id: MusicBoxFrame.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.text.MessageFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.sound.model.EchoNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.SoundEvent;
import org.mmarini.sound.model.SoundListener;
import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.SoundProcessorFactory;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.transfomers.LineConsumer;
import org.mmarini.sound.transfomers.LineProducer;
import org.mmarini.sound.transfomers.SampleProcessor;
import org.mmarini.sound.xml.DOMBuilder;
import org.mmarini.sound.xml.SAXBoxHandler;
import org.w3c.dom.Document;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: MusicBoxFrame.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class MusicBoxFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int SAMPLE_RATE = 44100;

	private static Log log = LogFactory.getLog(MusicBoxFrame.class);

	/**
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MusicBoxFrame frame = new MusicBoxFrame();
		frame.setVisible(true);
	}

	private AudioFormat audioFormat;

	private File file;

	private SoundProcessor soundProcessor;

	private JFileChooser fileChooser;

	private AbstractAction openAction;

	private AbstractAction saveAsAction;

	private AbstractAction saveAction;

	private AbstractAction newAction;

	private AbstractAction exitAction;

	private Palette palette;

	private PropertiesPane propertiesPane;

	private GraphComponent graphicsPane;

	private NodeListener nodeListener;

	private JSplitPane splitPane;

	private SoundListener soundListener;

	/**
	 * @throws HeadlessException
	 */
	public MusicBoxFrame() throws HeadlessException {
		init();
	}

	/**
         * 
         */
	private void createContent() {
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);
		JComponent toolbar = createToolbar();
		pane.add(toolbar, BorderLayout.NORTH);
		pane.add(splitPane, BorderLayout.CENTER);
	}

	/**
         * 
         */
	private void createFileChooser() {
		fileChooser = new JFileChooser();

		FileFilter fileFilter = new FileFilter() {

			/**
			 * @see javax.swing.JFileChooser#accept(java.io.File)
			 */
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				if (f.getName().endsWith(".xmb")) //$NON-NLS-1$
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return Messages.getString("MusicBoxFrame.filetype.description"); //$NON-NLS-1$
			}
		};
		fileChooser.setFileFilter(fileFilter);
	}

	/**
	 * @return
	 * 
	 */
	private void createGraphicsPane() {
		graphicsPane = new GraphComponent();
		MusicBox musicBox = new MusicBox();
		musicBox.setSoundProcessor(soundProcessor);
		graphicsPane.setMusicBox(musicBox);
		graphicsPane.addNodeListener(nodeListener);
	}

	/**
	 * @return
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText(Messages.getString("MusicBoxFrame.menu.file.test")); //$NON-NLS-1$
		JMenuItem item = new JMenuItem(newAction);
		menu.add(item);
		item = new JMenuItem(openAction);
		menu.add(item);
		menu.add(new JSeparator());
		item = new JMenuItem(saveAction);
		menu.add(item);
		item = new JMenuItem(saveAsAction);
		menu.add(item);
		menu.add(new JSeparator());
		item = new JMenuItem(exitAction);
		menu.add(item);
		menubar.add(menu);
		return menubar;
	}

	/**
         * 
         */
	protected void createNew() {
		SequenceNode seq = soundProcessor.createSequenceNode();
		soundProcessor.setTransformNode(seq);
		EchoNode lev = soundProcessor.createEchoNode();
		seq.add(lev);
		lev.setDelay(0.2f);
		lev.setSignalLevel(-6f);
		lev.setEchoLevel(-6f);

		saveAction.setEnabled(false);
	}

	/**
         * 
         * 
         */
	private void createSampleProcessor() {
		try {
			// URL inputResource =
			// Thread.currentThread().getContextClassLoader()
			// .getResource("prova1.wav");
			// AudioInputStream ais = AudioSystem
			// .getAudioInputStream(inputResource);

			TargetDataLine inLine = AudioSystem.getTargetDataLine(audioFormat);

			SourceDataLine outLine = (SourceDataLine) AudioSystem
					.getLine(new Line.Info(SourceDataLine.class));
			outLine.open(audioFormat);
			inLine.open();

			LineConsumer lineConsumer = new LineConsumer(outLine);
			LineProducer lineProducer = new LineProducer(inLine);
			SampleProcessor sampleProcessor = new SampleProcessor(lineConsumer,
					lineProducer);
			sampleProcessor.setKeeper(soundProcessor);
			// SampleProcessor sampleProcessor = new SampleProcessor(new
			// LineConsumer(
			// outLine), new StreamProducer(ais));

			outLine.start();
			inLine.start();

			sampleProcessor.init();
			sampleProcessor.start();
		} catch (LineUnavailableException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	private JToolBar createToolbar() {
		JToolBar toolBar = new JToolBar();
		JButton btn = new JButton(newAction);
		toolBar.add(btn);
		btn = new JButton(openAction);
		toolBar.add(btn);
		btn = new JButton(saveAction);
		toolBar.add(btn);
		btn = new JButton(saveAsAction);
		toolBar.add(btn);
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		btn = new JButton(exitAction);
		toolBar.add(btn);
		return toolBar;
	}

	/**
	 * @return
	 */
	private void createWorkingPane() {
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(palette, BorderLayout.EAST);
		top.add(graphicsPane, BorderLayout.CENTER);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top,
				propertiesPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(1);
	}

	/**
         * 
         */
	protected void exit() {
		System.exit(0);
	}

	/**
	 * @param box
	 * @param location
	 */
	private void focusOnLocation(MusicBox box, Point location) {
		GraphNode graphNode = box.getNode(location);
		if (graphNode != null) {
			TransformNode tr = graphNode.getTransformNode();
			if (tr != null) {
				/*
				 * Handle node selection to set properties pane
				 */
				propertiesPane.showTransformer(tr);
				graphicsPane.select(graphNode);
			}
		}
	}

	/**
	 * @return the file
	 */
	private File getFile() {
		return file;
	}

	/**
	 * @param event
	 */
	protected void handleNodeSelected(NodeEvent event) {
		MusicBox box = event.getMusicBox();
		Point location = event.getLocation();
		TransformNode node = palette.createSelectedNode();
		if (node != null) {
			box.insert(location, event.getDirection(), node);
			Point loc = box.locate(node);
			if (loc != null) {
				focusOnLocation(box, loc);
			}
		} else {
			focusOnLocation(box, location);
		}
	}

	/**
         * 
         */
	protected void handleWindowOpened() {
		splitPane.setDividerLocation(0.5);
	}

	/**
         * 
         */
	private void init() {
		log.debug("init"); //$NON-NLS-1$
		audioFormat = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
		soundProcessor = SoundProcessorFactory.getInstance()
				.create(audioFormat);
		soundListener = new SoundListener() {

			@Override
			public void nodeChanged(SoundEvent event) {
				repaint();
			}

			@Override
			public void rootChanged(SoundEvent event) {
				repaint();
			}

			@Override
			public void structureChanged(SoundEvent event) {
				repaint();
			}

		};
		soundProcessor.addSoundListener(soundListener);
		palette = new Palette();
		palette.setSoundProcessor(soundProcessor);
		propertiesPane = new PropertiesPane();
		nodeListener = new NodeListener() {

			@Override
			public void nodeSelected(NodeEvent nodeEvent) {
				handleNodeSelected(nodeEvent);
			}

		};

		createFileChooser();
		initAction();

		createGraphicsPane();
		createWorkingPane();
		createContent();
		WindowAdapter listener = new WindowAdapter() {

			/**
			 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				handleWindowOpened();
			}
		};
		addWindowListener(listener);
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Messages.getString("MusicBoxFrame.title")); //$NON-NLS-1$
		createNew();
		createSampleProcessor();
	}

	/**
         * 
         * 
         */
	private void initAction() {
		openAction = new AbstractAction() {

			/**
                 * 
                 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				open();
			}

		};

		saveAsAction = new AbstractAction() {

			/**
                 * 
                 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}

		};

		saveAction = new AbstractAction() {

			/**
                 * 
                 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}

		};

		newAction = new AbstractAction() {

			/**
                 * 
                 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				createNew();
			}

		};

		exitAction = new AbstractAction() {

			/**
                 * 
                 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}

		};
		initAction(newAction, "newAction"); //$NON-NLS-1$
		initAction(openAction, "openAction"); //$NON-NLS-1$
		initAction(saveAction, "saveAction"); //$NON-NLS-1$
		saveAction.setEnabled(false);
		initAction(saveAsAction, "saveAsAction"); //$NON-NLS-1$
		initAction(exitAction, "exitAction"); //$NON-NLS-1$
	}

	/**
	 * @param action
	 * @param key
	 */
	private void initAction(AbstractAction action, String key) {
		String value = Messages.getString("MusicBoxFrame." + key + ".name"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.NAME, value);
		value = Messages.getString("MusicBoxFrame." + key + ".tip"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.SHORT_DESCRIPTION, value);
		value = Messages.getString("MusicBoxFrame." + key + ".accelerator"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(value));
		value = Messages.getString("MusicBoxFrame." + key + ".mnemonic"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.MNEMONIC_KEY, Integer.valueOf(value.charAt(0)));
		value = Messages.getString("MusicBoxFrame." + key + ".icon"); //$NON-NLS-1$ //$NON-NLS-2$
		if (!value.startsWith("!")) { //$NON-NLS-1$
			URL url = getClass().getResource(value);
			if (url != null)
				action.putValue(Action.SMALL_ICON, new ImageIcon(url));
		}
	}

	/**
         * 
         */
	protected void open() {
		int rc = fileChooser.showOpenDialog(this);
		if (rc != JFileChooser.APPROVE_OPTION)
			return;
		File file = fileChooser.getSelectedFile();
		if (!file.canRead()) {
			String msg = MessageFormat.format(
					Messages.getString("MusicBoxFrame.message.unreadable"), //$NON-NLS-1$
					new Object[] { file });
			JOptionPane.showMessageDialog(this, msg,
					Messages.getString("MusicBoxFrame.error.title"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			SAXParserFactory builder = SAXParserFactory.newInstance();
			builder.setNamespaceAware(true);
			// builder.setValidating(true);
			// URL urlSchema =
			// Thread.currentThread().getContextClassLoader()
			// .getResource(SCHEMA_NAME);
			// if (urlSchema == null)
			// urlSchema = getClass().getResource(SCHEMA_NAME);
			// Schema schema = SchemaFactory.newInstance(
			// XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(urlSchema);
			// log.debug("schema=" + schema);
			// Validator val = schema.newValidator();
			// Source source = new StreamSource(file);
			// val.validate(source);
			// builder.setSchema(schema);
			SAXParser parser = builder.newSAXParser();
			SAXBoxHandler handler = new SAXBoxHandler();
			handler.setSoundProcessor(soundProcessor);
			parser.parse(file, handler);
			setFile(file);
			saveAction.setEnabled(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(),
					Messages.getString("MusicBoxFrame.error.title."),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
         * 
         */
	protected void save() {
		File file = getFile();
		TransformNode node = soundProcessor.getTransformNode();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			DOMBuilder.getInstance().build(doc, node);
			Transformer xmlTr = TransformerFactory.newInstance()
					.newTransformer();
			Source source = new DOMSource(doc);
			Result result = new StreamResult(file);
			xmlTr.transform(source, result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			JOptionPane.showMessageDialog(this, e.getMessage(),
					Messages.getString("MusicBoxFrame.error.title."),
					JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
         * 
         */
	protected void saveAs() {
		int rc = fileChooser.showSaveDialog(this);
		if (rc != JFileChooser.APPROVE_OPTION)
			return;
		File file = fileChooser.getSelectedFile();
		if (file.exists() && !file.canWrite()) {
			String msg = MessageFormat.format(
					Messages.getString("MusicBoxFrame.message.unwrittable"), //$NON-NLS-1$
					new Object[] { file });
			JOptionPane.showMessageDialog(this, msg,
					Messages.getString("MusicBoxFrame.error.title"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		setFile(file);
		saveAction.setEnabled(true);
		save();
	}

	/**
	 * @param file
	 *            the file to set
	 */
	private void setFile(File file) {
		this.file = file;
		if (file != null)
			setTitle(file.getPath());
	}
}