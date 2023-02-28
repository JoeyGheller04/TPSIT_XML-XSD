import java.io.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

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

    public static void main(String[] args) throws IOException {
        try {
            Validate.validate("catalog.xml", "catalog.xsd");
            System.out.println("Documento XML valido.");
        } catch (SAXException exception) {
            System.out.println("Documento XML NON valido:");
            System.out.println(exception.getMessage());
        }
    }
}
