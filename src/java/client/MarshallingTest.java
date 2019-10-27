package client;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.*;

public class MarshallingTest {

    public static void main(String[] args) {
        Room room = new Room(123,"Casa bonica","Sant Antoni N7","Barcelona",TypeDimension.SIMPLE,TypeLocation.INTERIOR,true, 13.22,new Requeriments(TypeSex.MAN,90,18,false, false));
        System.out.println(room.toString());
       
        // Marshaller
	try {
            File file = new File("room.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Room.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(room, file);
            jaxbMarshaller.marshal(room, System.out);

	} catch (JAXBException e) {        System.out.println(e);}
        
        // Unmarshaller
        try {

            File file = new File("room.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Room.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            room = (Room) jaxbUnmarshaller.unmarshal(file);
            System.out.println(room);

	} catch (JAXBException e) {System.out.println(e); }
        
    }
}
