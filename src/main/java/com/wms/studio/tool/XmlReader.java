package com.wms.studio.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.wms.studio.model.User;

/**
 * 
 * @author WMS
 * @version 1.5
 */
public class XmlReader {

	private Document doc = null;
	private SAXReader reader = new SAXReader();
	private String fileaddress = "Users.xml";
	private File file = new File(fileaddress);

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public XmlReader() {
		try {
			this.doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		List list = doc.selectNodes("//Users/User");
		if (list.size() == 0) {
			return null;
		}
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			User user = new User();
			Element element = (Element) iter.next();
			Iterator iterator = element.elementIterator();
			if (iterator.hasNext()) {
				Element el = (Element) iterator.next();
				user.setId(el.getText());// �û��˺�
				el = (Element) iterator.next();
				user.setName(el.getText());// �û�����
				el = (Element) iterator.next();
				user.setPassword(el.getTextTrim());// �û�����
				users.add(user);
			}
		}
		return users;
	}

	@SuppressWarnings("rawtypes")
	public boolean findById(String id) {
		List list = doc.selectNodes("//Users/User/id");
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			if (element.getText().equals(id)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public User getUser(String id) {
		User user = new User();
		List list = doc.selectNodes("//Users/User");
		if (list.size() == 0) {
			return null;
		}
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			Iterator iterator = element.elementIterator();
			if (iterator.hasNext()) {
				Element el = (Element) iterator.next();
				if (el.getText().equals(id)) {
					user.setId(el.getText());
					el = (Element) iterator.next();
					user.setName(el.getText());
					el = (Element) iterator.next();
					user.setPassword(el.getTextTrim());
					el = (Element) iterator.next();
					user.setRegisterDate(DatePaser.parseDate(el.getTextTrim()));
					return user;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public boolean updateUser(User user) {
		List list = doc.selectNodes("//Users/User");
		if (list.size() == 0) {
			return false;
		}
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			Iterator iterator = element.elementIterator();
			if (iterator.hasNext()) {
				Element el = (Element) iterator.next();
				if (el.getText().equals(user.getId())) {
					el = (Element) iterator.next();
					el.setText(user.getName());
					if (user.getPassword() == null
							|| "".equals(user.getPassword())) {
						return true;
					}
					el = (Element) iterator.next();
					el.setText(user.getPassword());
					return true;
				}
			}
		}
		return false;
	}

	public void save() {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter output = new XMLWriter(new FileOutputStream(file), format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerUser(User user) {
		Element root = doc.getRootElement();
		Element euser = root.addElement("User");
		euser.addElement("id").setText(user.getId());
		euser.addElement("name").setText(user.getName());
		euser.addElement("password").setText(user.getPassword());
		euser.addElement("registerDate").setText(
				DatePaser.formatDate(user.getRegisterDate()));
		this.save();
	}

}
