package client;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import model.*;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class XmlJsonTest {
    
    
    public static void main(String[] args) throws JAXBException{
        System.setProperty("javax.xml.bind.context.factory","org.eclipse.persistence.jaxb.JAXBContextFactory");
        Room room = new Room(123,"Casa bonica","Sant Antoni N7","Barcelona",TypeDimension.SIMPLE,TypeLocation.INTERIOR,true, 13.22,new Requeriments("JosepCastanyes@gmail.com",988264432,TypeSex.MAN,90,18,false, false));
        Renter renter = new Renter (1245,"mine",TypeSex.WOMAN,20,true,false);
        Credentials userpass = new Credentials("mine","craft");
        System.out.println("The system has\n"+room+"\n"+renter+"/n"+userpass);
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Rent the room? (y/n)");
        String response = keyboard.next();
        if(response.equals("y"))renter.setRoom(room);
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
        JAXBContext jc = JAXBContext.newInstance(Room.class);
        Marshaller marshaller = jc.createMarshaller();
        // Set the Marshaller media type to JSON or XML
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer sw = new StringWriter();
        marshaller.marshal(room, sw);
        System.out.println(sw.toString()); 
        
        System.out.println("\n---- ROOM JSON UNMARSHALLING TEST ----\n");
        jc = JAXBContext.newInstance(Room.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        // Set the Unmarshaller media type to JSON or XML
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        // Create the StreamSource by creating StringReader using the JSON input
        StreamSource json = new StreamSource(new StringReader(
        "{\n" +
        "   \"room\" : {\n" +
        "      \"id\" : 123,\n" +
        "      \"address\" : \"Sant Antoni N7\",\n" +
        "      \"city\" : \"Barcelona\",\n" +
        "      \"description\" : \"Casa bonica\",\n" +
        "      \"dimension\" : \"SIMPLE\",\n" +
        "      \"furniture\" : true,\n" +
        "      \"location\" : \"INTERIOR\",\n" +
        "      \"price\" : 13.22,\n" +
        "      \"req\" : {\n" +
        "         \"maxage\" : 90,\n" +
        "         \"minage\" : 18,\n" + 
        "         \"ownermail\" : \"JosepCastanyes@gmail.com\",\n" + 
        "         \"ownerphone\" : 988264432,\n" +
        "         \"pets\" : false,\n" +
        "         \"sex\" : \"MAN\",\n" +
        "         \"smokers\" : false\n" +
        "      }\n" +
        "   }\n" +
        "}"
        ));
        room = unmarshaller.unmarshal(json, Room.class).getValue();
        System.out.println(room);
        
        
        System.out.println("\n---- RENTER XML MARSHALLING TEST ----\n");
        file = new File("renter.xml");
        jaxbContext = JAXBContext.newInstance(Renter.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
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
        
        
        System.out.println("\n---- RENTER JSON UMARSHALLING TEST ----\n");
        jc = JAXBContext.newInstance(Renter.class);
        unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        if(response.equals("y"))
        {
            json = new StreamSource(new StringReader(
            "{\n" +
            "   \"renter\" : {\n" +
            "      \"id\" : 1245,\n" +
            "      \"username\" : \"mine\",\n" +
            "      \"sex\" : \"WOMAN\",\n" +
            "      \"age\" : 20,\n" +
            "      \"smoker\" : true,\n" +
            "      \"haspets\" : false,\n" +
            "      \"room\" : {\n" +
            "         \"id\" : 123,\n" +
            "         \"address\" : \"Sant Antoni N7\",\n" +
            "         \"city\" : \"Barcelona\",\n" +
            "         \"description\" : \"Casa bonica\",\n" +
            "         \"dimension\" : \"SIMPLE\",\n" +
            "         \"furniture\" : true,\n" +
            "         \"location\" : \"INTERIOR\",\n" +
            "         \"price\" : 13.22,\n" +
            "         \"req\" : {\n" +
            "            \"maxage\" : 90,\n" +
            "            \"minage\" : 18,\n" +
            "            \"pets\" : false,\n" +
            "            \"sex\" : \"MAN\",\n" +
            "            \"smokers\" : false\n" +
            "         }\n" +
            "      }\n" +
            "   }\n" +
            "}"
            ));
        }
        else
        {
             json = new StreamSource(new StringReader(
            "{\n" +
            "   \"renter\" : {\n" +
            "      \"id\" : 1245,\n" +
            "      \"username\" : \"mine\",\n" +
            "      \"sex\" : \"WOMAN\",\n" +
            "      \"age\" : 20,\n" +
            "      \"smoker\" : true,\n" +
            "      \"haspets\" : false\n" +
            "   }\n" +
            "}"
            ));   
        }
        // Getting the employee pojo again from the json
        renter = unmarshaller.unmarshal(json, Renter.class).getValue();
        System.out.println(renter);
        
        System.out.println("\n---- USERPASS XML MARSHALLING TEST ----\n");
        file = new File("userpass.xml");
        jaxbContext = JAXBContext.newInstance(Credentials.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(userpass, file);
        jaxbMarshaller.marshal(userpass, System.out);

        
        System.out.println("\n---- USERPASS JSON MARSHALLING TEST ----\n");
        jc = JAXBContext.newInstance(Credentials.class);
        marshaller = jc.createMarshaller();
        // Set the Marshaller media type to JSON or XML
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        sw = new StringWriter();
        marshaller.marshal(userpass, sw);
        System.out.println(sw.toString()); 
        
    }
}
