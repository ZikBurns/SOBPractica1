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
        
        System.out.println("--- ROOM MARSHALLING TEST ---");
	try {
            File file = new File("room.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Room.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(room, file);
            jaxbMarshaller.marshal(room, System.out);

	} catch (JAXBException e) {        System.out.println(e);}
        
        System.out.println("--- ROOM UNMARSHALLING TEST ---");
        try {

            File file = new File("room.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Room.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            room = (Room) jaxbUnmarshaller.unmarshal(file);
            System.out.println(room);

	} catch (JAXBException e) {System.out.println(e); }
        
        Renter renter = new Renter (1245,"mine","craft",TypeSex.WOMAN,20,true,false);
        System.out.println("--- RENTER MARSHALLING TEST ---");
	try {
            File file = new File("renter.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Renter.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(renter, file);
            jaxbMarshaller.marshal(renter, System.out);

	} catch (JAXBException e) {        System.out.println(e);}
        
        System.out.println("--- RENTER UNMARSHALLING TEST ---");
        try {

            File file = new File("renter.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Renter.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            renter = (Renter) jaxbUnmarshaller.unmarshal(file);
            System.out.println(renter);

	} catch (JAXBException e) {System.out.println(e); }
        
    }
}
