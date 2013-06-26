/*
 * SAXBoxHandler.java
 *
 * $Id: SAXBoxHandler.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 *
 * 08/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.xml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.sound.model.AbstractCompositeNode;
import org.mmarini.sound.model.AutoMuteNode;
import org.mmarini.sound.model.ConnectNode;
import org.mmarini.sound.model.EchoNode;
import org.mmarini.sound.model.LevelMeterNode;
import org.mmarini.sound.model.MixerNode;
import org.mmarini.sound.model.ModulateNode;
import org.mmarini.sound.model.SequenceNode;
import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.ToneShiftNode;
import org.mmarini.sound.model.TransformNode;
import org.mmarini.sound.model.VolumeNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SAXBoxHandler.java,v 1.2 2008/11/20 21:07:26 marco Exp $
 * 
 */
public class SAXBoxHandler extends DefaultHandler implements NodeXMLConstants {
	private static Log log = LogFactory.getLog(SAXBoxHandler.class);

	private StringBuffer text;

	private List<List<TransformNode>> stack;

	private String attrName;

	private SoundProcessor soundProcessor;

	/**
         * 
         */
	public SAXBoxHandler() {
		text = new StringBuffer();
		stack = new ArrayList<List<TransformNode>>(0);
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		getText().append(ch, start, length);
	}

	/**
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	private TransformNode createNode(String className)
			throws ClassNotFoundException {
		TransformNode tr = null;
		if (ConnectNode.class.getName().equals(className)) {
			tr = soundProcessor.createConnectNode();
		} else if (EchoNode.class.getName().equals(className)) {
			tr = soundProcessor.createEchoNode();
		} else if (AutoMuteNode.class.getName().equals(className)) {
			tr = soundProcessor.createAutoMuteNode();
		} else if (ModulateNode.class.getName().equals(className)) {
			tr = soundProcessor.createModulateNode();
		} else if (VolumeNode.class.getName().equals(className)) {
			tr = soundProcessor.createVolumeNode();
		} else if (LevelMeterNode.class.getName().equals(className)) {
			tr = soundProcessor.createLevelMeterNode();
		} else if (SequenceNode.class.getName().equals(className)) {
			tr = soundProcessor.createSequenceNode();
		} else if (MixerNode.class.getName().equals(className)) {
			tr = soundProcessor.createMixerNode();
		} else if (ToneShiftNode.class.getName().equals(className)) {
			tr = soundProcessor.createToneShiftNode();
		} else {
			throw new ClassNotFoundException(className);
		}
		return tr;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		TransformNode node = pop().get(0);
		soundProcessor.setTransformNode(node);
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (!NAME_SPACE.equals(uri))
			return;
		if (ATTRIBUTE_ELEMENT.equals(localName)) {
			setAttribute();
		} else if (TRANSFORMER_ELEMENT.equals(localName)) {
			List<TransformNode> list = pop();
			TransformNode tr = list.get(0);
			int n = list.size();
			if (n > 1) {
				((AbstractCompositeNode) tr).addAll(list.subList(1, n));
			}
			list = peek();
			list.add(tr);
			log.debug("Processed " + tr);
		}
	}

	/**
	 * @return the attrName
	 */
	private String getAttrName() {
		return attrName;
	}

	/**
	 * @return the stack
	 */
	private List<List<TransformNode>> getStack() {
		return stack;
	}

	/**
	 * @return the text
	 */
	private StringBuffer getText() {
		return text;
	}

	/**
	 * @return
	 */
	private List<TransformNode> peek() {
		return getStack().get(getStack().size() - 1);
	}

	/**
	 * @return
	 */
	private List<TransformNode> pop() {
		return getStack().remove(getStack().size() - 1);
	}

	/**
	 * @param tr
	 */
	private void push(List<TransformNode> tr) {
		getStack().add(tr);
	}

	/**
	 * @throws SAXException
	 */
	private void setAttribute() throws SAXException {
		String name = getAttrName();
		String value = getText().toString();
		TransformNode tr = peek().get(0);
		try {
			BeanInfo info = Introspector.getBeanInfo(tr.getClass());
			PropertyDescriptor[] prs = info.getPropertyDescriptors();
			PropertyDescriptor pr = null;
			for (int i = 0; pr == null && i < prs.length; ++i) {
				if (name.equals(prs[i].getName())) {
					pr = prs[i];
				}
			}
			if (pr != null) {
				Method wrm = pr.getWriteMethod();
				wrm.invoke(tr, Float.parseFloat(value));
			}
		} catch (IntrospectionException e) {
			log.error(e.getMessage(), e);
			throw new SAXException(e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			throw new SAXException(e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new SAXException(e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
			throw new SAXException(e);
		}
	}

	/**
	 * @param attrName
	 *            the attrName to set
	 */
	private void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	/**
	 * @param soundProcessor
	 *            the soundProcessor to set
	 */
	public void setSoundProcessor(SoundProcessor soundProcessor) {
		this.soundProcessor = soundProcessor;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		push(new ArrayList<TransformNode>(1));
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (!NAME_SPACE.equals(uri))
			return;
		if (ATTRIBUTE_ELEMENT.equals(localName)) {
			setAttrName(attributes.getValue(NAME_ATTR));
			getText().setLength(0);
		} else if (TRANSFORMER_ELEMENT.equals(localName)) {
			String className = attributes.getValue(CLASS_ATTR);
			try {
				TransformNode tr = createNode(className);
				log.debug("Created " + tr);
				List<TransformNode> list = new ArrayList<TransformNode>(1);
				list.add(tr);
				push(list);
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage(), e);
				throw new SAXException(e);
			}
		}
	}
}
