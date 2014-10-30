package doppiogroup;
/*
 * author Nergui Badarch
 * Date Oct 5, 2014
 * Store xml into mysql and create xml.
 * 
 */

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;

import java.io.File;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App {

	public static void main(String[] args) {
		ArrayList<Order> orderList = new ArrayList<Order>();
		BasicConfigurator.configure();
		final String purchaseOrderXmlPath="PurchaseOrder.xml";
		final String purchaseOrderXsdPath="PurchaseOrderSchema.xsd";
		final String createXmlPath="d:\\xmlfile.xml";

		if (Validation(purchaseOrderXmlPath, purchaseOrderXsdPath) || true) {

			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
			try {
				File file = new File(purchaseOrderXmlPath);
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				System.out.println("Root element: "
						+ doc.getDocumentElement().getNodeName());

				NodeList HeadnodeLst = doc.getElementsByTagName("OrderHeader");
				Node HeaderNode = HeadnodeLst.item(0);

				Order order = null;

				if (HeaderNode.getNodeType() == Node.ELEMENT_NODE) {

					System.out.println("-------------header---------------");
					Element headerElmnt = (Element) HeaderNode;

					System.out.println("TradingPartnerId : "
							+ GetValue(headerElmnt, "TradingPartnerId"));
					System.out.println("PurchaseOrderNumber : "
							+ GetValue(headerElmnt, "PurchaseOrderNumber"));
					System.out.println("PurchaseOrderDate : "
							+ GetValue(headerElmnt, "PurchaseOrderDate"));

					order = new Order(GetValue(headerElmnt,
							"TradingPartnerId"), GetValue(headerElmnt,
							"PurchaseOrderNumber"), GetValue(headerElmnt,
							"PurchaseOrderDate"));
					orderList.add(order);

					session.save(order);

					System.out.println("----------------------------");
				}

				NodeList nodeLst = doc.getElementsByTagName("OrderLine");

				System.out.println("Information of all OrderLine");

				lineItem lItem;

				for (int s = 0; s < nodeLst.getLength(); s++) {

					Node fstNode = nodeLst.item(s);

					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

						Element fstElmnt = (Element) fstNode;

						System.out.println("LineSequenceNumber : "
								+ GetValue(fstElmnt, "LineSequenceNumber"));
						System.out.println("EAN : "
								+ GetValue(fstElmnt, "EAN"));
						System.out.println("OrderQtyx: "
								+ GetValue(fstElmnt, "OrderQty"));
						System.out.println("----------------------------");

						lItem = new lineItem(Integer.parseInt(GetValue(
								fstElmnt, "LineSequenceNumber")), GetValue(
								fstElmnt, "EAN"), GetValue(
								fstElmnt, "OrderQty"));

						orderList.get(orderList.size() - 1).getLineItems()
								.add(lItem);

						lItem.setOrder(orderList.get(orderList.size() - 1));

						session.save(lItem);
						 if ( s % 20 == 0 ) { 
						      
						        session.flush();
						        session.clear();
						    }

					}

				}

				session.getTransaction().commit();
				session.close();
				System.out.println("Great! Order was saved");
				if(createXML(order,createXmlPath)){
					System.out.println("Great! Create xml successfull");
				}else {
					System.out.println("Great! Create xml not successfull");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		

		}

	}
	public static boolean createXML(Order o,String xmlFilePath){
			boolean flag=true;
		try {
			
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("order");
			document.appendChild(root);
			Element header = document.createElement("header");

			root.appendChild(header);
			
			Element OrderHeader = document.createElement("OrderHeader");

			header.appendChild(OrderHeader);
			
			Element TradingPartnerId = document.createElement("TradingPartnerId");
			TradingPartnerId.appendChild(document.createTextNode(o.getTradingPartnerId().toString()));
			OrderHeader.appendChild(TradingPartnerId);
			
			Element PurchaseOrderNumber = document.createElement("PurchaseOrderNumber");
			PurchaseOrderNumber.appendChild(document.createTextNode(o.getPurchaseOrderNumber().toString()));
			OrderHeader.appendChild(PurchaseOrderNumber);
			
			Element PurchaseOrderDate = document.createElement("PurchaseOrderDate");
			PurchaseOrderDate.appendChild(document.createTextNode(o.getPurchaseOrderDate().toString()));
			OrderHeader.appendChild(PurchaseOrderDate);
			
			
			// line items
			Element LineItems = document.createElement("LineItems");

			root.appendChild(LineItems);

			for(lineItem li:o.getLineItems()){
				
				
				Element LineItem = document.createElement("LineItem");

				LineItems.appendChild(LineItem);
				
				Element OrderLine = document.createElement("OrderLine");

				LineItem.appendChild(OrderLine);
				
				Element LineSequenceNumber = document.createElement("LineSequenceNumber");
				LineSequenceNumber.appendChild(document.createTextNode(li.getLineSequenceNumber()+""));
				OrderLine.appendChild(LineSequenceNumber);

				Element EAN = document.createElement("EAN");
				EAN.appendChild(document.createTextNode(li.getEAN()));
				OrderLine.appendChild(EAN);
				
				Element OrderQtyx = document.createElement("OrderQtyx");
				OrderQtyx.appendChild(document.createTextNode(li.getOrderQtyx()+""));
				OrderLine.appendChild(OrderQtyx);
			}
			

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));


			transformer.transform(domSource, streamResult);

			

		} catch (ParserConfigurationException pce) {
			flag=false;
			pce.printStackTrace();
			return true;
		} catch (TransformerException tfe) {
			flag=false;
			tfe.printStackTrace();
		}
		return flag;
	}
	public static String GetValue(Element e, String tag) {

		NodeList list = e.getElementsByTagName(tag);
		Element element = (Element) list.item(0);
		NodeList listNodes = element.getChildNodes();

		return ((Node) listNodes.item(0)).getNodeValue();
	}

	public static boolean Validation(String xmlFile, String xsdFile) {
		boolean flag = true;
		final SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema;
		final Validator validator;

		try {
			schema = schemaFactory.newSchema(new File(xsdFile));
			validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlFile)));
		} catch (SAXException e1) {
			flag = false;
			e1.printStackTrace();
		} catch (Exception e) {
			flag = false;

		}
		return flag;
	}
}