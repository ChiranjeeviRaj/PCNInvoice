package uk.pcn.invoice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.pcn.invoice.service.PCNException;


@Component
public class XmlUtils {
	
	private Logger log = Logger.getLogger(XmlUtils.class);

	public XmlUtils() {
	}

	public String format(String unformattedXml) {
		try {
			Source xmlInput = new StreamSource(new StringReader(unformattedXml));
			StreamResult xmlOutput = new StreamResult(new StringWriter());

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(xmlInput, xmlOutput);

			return xmlOutput.getWriter().toString();

		} catch (Exception e) {
			throw new PCNException(e);
		}
	}

	public String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (Exception e) {
			throw new PCNException(e);
		}
		return sw.toString();
	}

	public String toXml(Document doc) throws TransformerException {

		TransformerFactory xformFactory = TransformerFactory.newInstance();
		Transformer transformer = xformFactory.newTransformer();
		Source input = new DOMSource(doc);
		StreamResult xmlOutput = new StreamResult(new StringWriter());

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(input, xmlOutput);

		return xmlOutput.getWriter().toString();
	}

	public String marshall(Object obj) {
		return marshall(obj, null);
	}

	public String marshall(Object obj, String schemaLocation) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			if (schemaLocation != null) {
				jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
			}

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(obj, writer);
			String xml = writer.toString();
			writer.close();
			return xml;

		} catch (Exception e) {
			throw new PCNException(e);
		}
	}

	public Object unmarshal(Class<?> clazz, String xml) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return jaxbUnmarshaller.unmarshal(new File(xml));
		} catch (JAXBException e) {
			throw new PCNException(e);
		}
	}

	public String xslTransform(String srcXml, String xslt) {

		byte srcXmlBytes[] = srcXml.getBytes();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(srcXmlBytes);

		Source xmlSource = new StreamSource(byteArrayInputStream);
		Source xsltSource = new StreamSource( this.getClass().getResourceAsStream(xslt));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Result permission = new javax.xml.transform.stream.StreamResult(out);
		SAXTransformerFactory tFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
		String result = "";
		try {
			Transformer transformer = tFactory.newTransformer(xsltSource);
			transformer.transform(xmlSource, permission);
			result = out.toString("UTF-8");
			return result;
		} catch (Exception e) {
			throw new PCNException(e);
		}
	}
	
	public Node getNodeByTag(NodeList nodeList, String attrName, String value){
		for (int i=0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
               if( eElement.getAttribute(attrName).equalsIgnoreCase(value))
            	   return node;
            }
		}
		return null;
	}
	public NodeList getNodeListByName(String tag, String schemaLocation){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            Document doc = null;;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(new File(schemaLocation));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // optional, but recommended
            // read this -
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName(tag);
	}
	

	//file:\E:\workspace-pcn\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\PCNInvoice\WEB-INF\classes\rate_cards\parcels\xls\THERMOSCREEENS_LTD_YODEL.xml
	public String getFile(String fileName, String type){
    	String classpathLocation = "WEB-INF/classes/"  +"rate_cards" + File.separatorChar + type +  
    			File.separatorChar + "xls"+ File.separatorChar + fileName;
    	log.debug("classpathLocation :" +classpathLocation);

		return getClassPath() + classpathLocation;
		//Chanege the retrun type for remote openshift server ***********************8
    	//String path1 = "/var/lib/openshift/53393c4a5973ca750300004d/jbossews/work/Catalina/localhost/_/" + classpathLocation;
    	//return path1;
	}

	/**
	 * It returns context root of the app on tomcat.
	 * openShift /var/lib/openshift/53393c4a5973ca750300004d/jbossews/work/Catalina/localhost/_/
	 * @return
	 */
	public String getClassPath(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String webContentRoot = servletContext.getContextPath();
        log.debug("webContentRoot :" +webContentRoot);
		URL url = XmlUtils.class.getResource("XmlUtils.class");
        log.debug("url :" +url);
        if(webContentRoot == null || webContentRoot == "") {
        	webContentRoot = "_";
            log.debug("webContentRoot11 :" +webContentRoot);
        }
		String[] split = StringUtils.split(url.toString(), webContentRoot);
		log.debug("split :" +split);
		String path =  split[0].replaceAll("file:", "");
		log.debug("path :" +path);
		return path + webContentRoot + File.separatorChar;
	}
}