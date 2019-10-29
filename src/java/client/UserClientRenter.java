/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.StringWriter;
import java.io.Writer;
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
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 *
 * @author ZikBurns
 */
public class UserClientRenter {
    private final WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/rest/api/v1";

    public UserClientRenter() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("renter");
    }
    
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
    
    
    //requereix que el renter estigui identificat
    public Response getRenter()
    {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_JSON).get();
    }
    
    public Response getRenters()
    {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_JSON).get();
    }

    public Response postRenter(Renter renter) throws JAXBException{
        String json=this.marshallJson(renter);
        return webTarget.request().post(Entity.entity(json, MediaType.TEXT_PLAIN), Response.class);
    }
    
    public Response putUpdateRenter(Renter renter) throws JAXBException
    {
        String json=this.marshallJson(renter);
        return webTarget.request().put(Entity.entity(json, MediaType.TEXT_PLAIN), Response.class);
    }
    
    public Response deleteRenter() throws JAXBException
    {
        return webTarget.request().delete();
    }
    
    public Response postRenterRoom(Room room) throws JAXBException{
        String json=this.marshallJson(room);
        return webTarget.request().post(Entity.entity(json, MediaType.TEXT_PLAIN), Response.class);
    }
    
    public static void main(String[] args){
        
        
        
    }
    
}
