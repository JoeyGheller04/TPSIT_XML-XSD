import java.io.*;

import javax.xml.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Validate {

    public static void validate(String XMLdocument, String XSDschema) throws SAXException, IOException {
        // creazione di uno schema XSD a partire dal file
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaFile = new File(XSDschema);
        Schema schema = factory.newSchema(schemaFile);
        // creazione di un validatore rispetto allo schema XSD
        Validator validator = schema.newValidator();
        // validazione del documento XML
        Source source = new StreamSource(XMLdocument);
        validator.validate(source);
    }

    public static void parseCatalog(Element catalogElement) {
        // Otteniamo la lista di nodi PLANT dal catalogo
        NodeList plantNodes = catalogElement.getElementsByTagName("PLANT");
        //ciascun nodo PLANT viene analizzato e stampato
        for (int i = 0; i < plantNodes.getLength(); i++) {

          Element plantElement = (Element) plantNodes.item(i);

          String commonName = plantElement.getElementsByTagName("COMMON").item(0).getTextContent();
          String botanicalName = plantElement.getElementsByTagName("BOTANICAL").item(0).getTextContent();
          String price = plantElement.getElementsByTagName("PRICE").item(0).getTextContent();
          String currency = plantElement.getElementsByTagName("PRICE").item(0).getAttributes().getNamedItem("CURRENCY").getTextContent();
          
          System.out.println(commonName + " (" + botanicalName + "): " + price + " " + currency);
        }
    }

    public static void main(String[] args) throws SAXException, IOException, Exception {
        //valida documento XML
        try {
            Validate.validate("catalog.xml", "catalog.xsd");
            System.out.println("Documento XML valido.");
        } catch (SAXException exception) {
            System.out.println("Documento XML NON valido:");
            System.out.println(exception.getMessage());
        }
        //stampa tutte le piante
        try {
            File xmlFile = new File("catalog.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            Element catalogElement = doc.getDocumentElement();
            parseCatalog(catalogElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}