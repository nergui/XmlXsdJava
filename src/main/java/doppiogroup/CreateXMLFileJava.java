package doppiogroup;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXMLFileJava {

	public static final String xmlFilePath = "d:\\xmlfile.xml";

	public static void main(String argv[]) {

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
			TradingPartnerId.appendChild(document.createTextNode("TradingPartnerId"));
			OrderHeader.appendChild(TradingPartnerId);
			
			Element PurchaseOrderNumber = document.createElement("PurchaseOrderNumber");
			PurchaseOrderNumber.appendChild(document.createTextNode("PurchaseOrderNumber"));
			OrderHeader.appendChild(PurchaseOrderNumber);
			
			Element PurchaseOrderDate = document.createElement("PurchaseOrderDate");
			PurchaseOrderDate.appendChild(document.createTextNode("PurchaseOrderDate"));
			OrderHeader.appendChild(PurchaseOrderDate);
			
			
			// line items
			Element LineItems = document.createElement("LineItems");

			root.appendChild(LineItems);
			
			Element LineItem = document.createElement("LineItem");

			LineItems.appendChild(LineItem);
			
			Element OrderLine = document.createElement("OrderLine");

			LineItem.appendChild(OrderLine);


			Element LineSequenceNumber = document.createElement("LineSequenceNumber");
			LineSequenceNumber.appendChild(document.createTextNode("LineSequenceNumber"));
			OrderLine.appendChild(LineSequenceNumber);

			Element EAN = document.createElement("EAN");
			EAN.appendChild(document.createTextNode("EAN"));
			OrderLine.appendChild(EAN);
			
			Element OrderQtyx = document.createElement("OrderQtyx");
			OrderQtyx.appendChild(document.createTextNode("OrderQtyx"));
			OrderLine.appendChild(OrderQtyx);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));


			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}