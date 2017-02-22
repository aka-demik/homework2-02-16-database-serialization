package persistent.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class Serializer {

    public static void save(Object obj, File file,  Class... classesToBeBound) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(classesToBeBound);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (OutputStream os = new FileOutputStream(file)) {
            marshaller.marshal(obj, os);
        }
    }

    public static Object load(File file, Class... classesToBeBound) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        try (InputStream is = new FileInputStream(file)) {
            return jaxbUnmarshaller.unmarshal(is);
        }
    }
}
