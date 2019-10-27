package client;

import java.io.File;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import model.*;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class MarshallingTest {

    public static void main(String[] args) throws JAXBException{
        Room room = new Room(123,"Casa bonica","Sant Antoni N7","Barcelona",TypeDimension.SIMPLE,TypeLocation.INTERIOR,true, 13.22,new Requeriments(TypeSex.MAN,90,18,false, false));
        Renter renter = new Renter (1245,"mine","craft",TypeSex.WOMAN,20,true,false);
        File file;
        JAXBContext jaxbContext;
        System.out.println("\n---- ROOM XML MARSHALLING TEST ----\n");
        file = new File("room.xml");
        jaxbContext = JAXBContext.newInstance(Room.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(room, file);
        jaxbMarshaller.marshal(room, System.out);

        
        System.out.println("\n---- ROOM XML UNMARSHALLING TEST ----\n");
        file = new File("room.xml");
        jaxbContext = JAXBContext.newInstance(Room.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        room = (Room) jaxbUnmarshaller.unmarshal(file);
        System.out.println(room);

        System.out.println("\n---- ROOM JSON MARSHALLING TEST ----\n");
        // Create a JaxBContext
        JAXBContext jc = JAXBContext.newInstance(Room.class);
        // Create the Marshaller Object using the JaxB Context
        Marshaller marshaller = jc.createMarshaller();
        // Set the Marshaller media type to JSON or XML
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        // Set it to true if you need to include the JSON root element in the JSON output
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        // Set it to true if you need the JSON output to formatted
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // Marshal the employee object to JSON and print the output to console
        marshaller.marshal(room, System.out);
        
        
        System.out.println("\n---- ROOM JSON UNMARSHALLING TEST ----\n");
        // Create a JaxBContext
        jc = JAXBContext.newInstance(Room.class);
        // Create the Unmarshaller Object using the JaxB Context
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        // Set the Unmarshaller media type to JSON or XML
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        // Set it to true if you need to include the JSON root element in the JSON input
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        // Create the StreamSource by creating StringReader using the JSON input
        StreamSource json = new StreamSource(new StringReader(
        "INSERT JSON FORMAT FOR JSONED ROOM"
        ));
        // Getting the employee pojo again from the json
        room = unmarshaller.unmarshal(json, Room.class).getValue();
        System.out.println(room);
        
        System.out.println("\n---- RENTER XML MARSHALLING TEST ----\n");
        file = new File("renter.xml");
        jaxbContext = JAXBContext.newInstance(Renter.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(renter, file);
        jaxbMarshaller.marshal(renter, System.out);
        
        System.out.println("\n---- RENTER XML UNMARSHALLING TEST ----\n");
        file = new File("renter.xml");
        jaxbContext = JAXBContext.newInstance(Renter.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        renter = (Renter) jaxbUnmarshaller.unmarshal(file);
        System.out.println(renter);
        
        System.out.println("\n---- RENTER JSON MARSHALLING TEST ----\n");
        jc = JAXBContext.newInstance(Renter.class);
        marshaller = jc.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(renter, System.out);
        
        System.out.println("\n---- RENTER JSON MARSHALLING TEST ----\n");
        jc = JAXBContext.newInstance(Renter.class);
        unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        json = new StreamSource(new StringReader(
        "INSERT JSON FORMAT FOR JSONED Renter"
        ));
        // Getting the employee pojo again from the json
        renter = unmarshaller.unmarshal(json, Renter.class).getValue();
        System.out.println(renter);
        
    }
}
