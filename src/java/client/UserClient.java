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
import model.Tenant;
import model.Room;
import model.TypeSex;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 *
 * @author ZikBurns
 */
public class UserClient {
    private final WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/rest/api/v1";

    public UserClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }
    /*
    public String marshallJson(Tenant tenant) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(Tenant.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer sw = new StringWriter();
        marshaller.marshal(tenant, sw);
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
    
    //requereix que el tenant estigui identificat
    public Response getTenant(int id)
    {
        WebTarget resource = client.target(BASE_URI).path("tenant/"+id);
        return resource.request(MediaType.APPLICATION_JSON).get();
    }
    
    public Response getTenants()
    {
        WebTarget resource = client.target(BASE_URI).path("tenant");
        return resource.request(MediaType.APPLICATION_JSON).get();
    }

    public Response postTenant(Tenant tenant){
        WebTarget resource = client.target(BASE_URI).path("tenant");
        return resource.request().post(Entity.entity(tenant, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public Response putUpdateTenant(Tenant tenant) 
    {
        WebTarget resource = client.target(BASE_URI).path("tenant/"+tenant.getId());
        return resource.request().put(Entity.entity(tenant, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public Response deleteTenant(int id)
    {
        WebTarget resource = client.target(BASE_URI).path("tenant/"+id);
        return resource.request().delete();
    }
    
    public Response postTenantRoom(int id, Room room){
        WebTarget resource = client.target(BASE_URI).path("tenant/"+id+"/rent");
        return resource.request().post(Entity.entity(room, MediaType.APPLICATION_JSON), Response.class);
    }
    
    public static void main(String[] args){
        UserClient client = new UserClient();
        
        Response response = client.getTenants();
        System.out.println("Response get: \n" + response.readEntity(Collection.class) + "\n  Status: " + response.getStatus());
        
        response = client.getTenant(2);
        System.out.println("Response get: \n" + response.readEntity(Tenant.class) + "\n  Status: " + response.getStatus());
        
        Tenant tenant=new Tenant(10,"josepmaria","catorze",TypeSex.MAN,46,true,true);
        response = client.postTenant(tenant);
        System.out.println("Response post: \n" + response.readEntity(Tenant.class) + "\n  Status: " + response.getStatus());
        
        tenant=new Tenant(10,"josepmaria","quinze",TypeSex.MAN,46,true,true);
        response = client.putUpdateTenant(tenant);
        System.out.println("Response update: \n" + response.readEntity(Tenant.class) + "\n  Status: " + response.getStatus());
        
        response = client.deleteTenant(2);
        System.out.println("Response delete: \n" + response.readEntity(Tenant.class) + "\n  Status: " + response.getStatus());
        
        Room room=new Room();
        response = client.postTenantRoom(3, room);
        System.out.println("Response delete: \n" + response.readEntity(Tenant.class) + "\n  Status: " + response.getStatus());
        
        response = client.getTenants();
        System.out.println("Response get: \n" + response.readEntity(Collection.class) + "\n  Status: " + response.getStatus());
        
    }
    
}
