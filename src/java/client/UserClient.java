/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import model.Renter;
import model.Room;
import model.TypeSex;

/**
 *
 * @author ZikBurns
 */
public class UserClient {
    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/rest/api/v1";

    public UserClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }
    /*
    public String marshallJson(Renter renter) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(Renter.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer sw = new StringWriter();
        marshaller.marshal(renter, sw);
        return(sw.toString()); 
    }
    
    public String marshallJson(Room room) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(Room.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer sw = new StringWriter();
        marshaller.marshal(room, sw);
        return(sw.toString()); 
    }
    */
    
    //requereix que el renter estigui identificat
    public Response getRenter(int id)
    {
        WebTarget resource = client.target(BASE_URI).path("renter/"+id);
        return resource.request(MediaType.APPLICATION_JSON).get();
    }
    
    public Response getRenters()
    {
        WebTarget resource = client.target(BASE_URI).path("renter");
        return resource.request(MediaType.APPLICATION_JSON).get();
    }

    public Response postRenter(Renter renter){
        WebTarget resource = client.target(BASE_URI).path("renter");
        return resource.request().post(Entity.entity(renter, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public Response putUpdateRenter(Renter renter) 
    {
        WebTarget resource = client.target(BASE_URI).path("renter/"+renter.getId());
        return resource.request().put(Entity.entity(renter, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public Response deleteRenter(int id)
    {
        WebTarget resource = client.target(BASE_URI).path("renter/"+id);
        return resource.request().delete();
    }
    
    public Response postRenterRoom(int id, Room room){
        WebTarget resource = client.target(BASE_URI).path("renter/"+id+"/rent");
        return resource.request().post(Entity.entity(room, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public static void main(String[] args){
        UserClient client = new UserClient();
        
        Response response = client.getRenters();
        System.out.println("Response get: \n" + response.readEntity(Collection.class) + "\n  Status: " + response.getStatus());
        
        response = client.getRenter(2);
        System.out.println("Response get: \n" + response.readEntity(Renter.class) + "\n  Status: " + response.getStatus());
        
        Renter renter=new Renter(10,"josepmaria","catorze",TypeSex.MAN,46,true,true);
        response = client.postRenter(renter);
        System.out.println("Response post: \n" + response.readEntity(Renter.class) + "\n  Status: " + response.getStatus());
        
        renter=new Renter(10,"josepmaria","quinze",TypeSex.MAN,46,true,true);
        response = client.putUpdateRenter(renter);
        System.out.println("Response update: \n" + response.readEntity(Renter.class) + "\n  Status: " + response.getStatus());
        
        response = client.deleteRenter(2);
        System.out.println("Response delete: \n" + response.readEntity(Renter.class) + "\n  Status: " + response.getStatus());
        
        Room room=new Room();
        response = client.postRenterRoom(3, room);
        System.out.println("Response delete: \n" + response.readEntity(Renter.class) + "\n  Status: " + response.getStatus());
        
        response = client.getRenters();
        System.out.println("Response get: \n" + response.readEntity(Collection.class) + "\n  Status: " + response.getStatus());
        
    }
    
}
