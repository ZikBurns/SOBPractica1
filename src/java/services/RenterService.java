/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.StringReader;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Renter;
import model.Room;
import model.TypeSex;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

@Path("/tenant")
public class RenterService {
    protected EntityManager em;
    
    public RenterService(EntityManager em){
        this.em=em;
    }
    
    private Renter unmarshalljsonRenter(String jsonstring) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(Renter.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        StreamSource json = new StreamSource(new StringReader(jsonstring));
        return unmarshaller.unmarshal(json, Renter.class).getValue();
    }
    
    private Room unmarshalljsonRoom(String jsonstring) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(Room.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
        StreamSource json = new StreamSource(new StringReader(jsonstring));
        return unmarshaller.unmarshal(json, Room.class).getValue();
    }
    
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postRenter(String jsonstring) throws JAXBException
    {
        Renter renter = this.unmarshalljsonRenter(jsonstring);
        return Response.ok().entity(renter).build();
    }
    
     public Renter createRenter(String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Renter renter = new Renter();
        renter.setUsername(user);
        renter.setPassword(pass);
        renter.setSex(sex);
        renter.setAge(age);
        renter.setSmoker(smoker);
        renter.setHaspets(haspets);
        em.persist(renter);
        return renter;
    }
    
     
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getjsonAllRenters(){
         return Response.ok().entity(this.queryAllRenters()).build();
    }
     
    public Collection<Renter> queryAllRenters() {
        return (Collection<Renter>) em.createQuery(
                "SELECT r FROM Renter r").getResultList();
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRenterwithid(@PathParam("id") int id){
        return Response.ok().entity(this.queryRenterwithid(id)).build();
        //What to do if response is null?? Ask the teacher
    }
    
    public Renter queryRenterwithid(int id){
        String query="SELECT r FROM Renter r WHERE r.id = :id";
        try {
        return (Renter) em.createQuery(query)
                        .setParameter("id", id)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /*TODO UpdateRenter as a PUT*/
    @PUT    
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response putUpdateRenter(@PathParam("id") int id, String jsonstring) throws JAXBException
    {
        Renter renter = this.unmarshalljsonRenter(jsonstring);
        renter=this.updateRenter(id,renter.getUsername(),renter.getPassword(),renter.getSex(),renter.getAge(),renter.isSmoker(),renter.isHaspets());
        return Response.ok().entity(renter).build();
    }
    
    public Renter updateRenter(int id,String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            renter.setUsername(user);
            renter.setPassword(pass);
            renter.setSex(sex);
            renter.setAge(age);
            renter.setSmoker(smoker);
            renter.setHaspets(haspets);
            return renter;
        }
        else return null;
        
    }
    
    @DELETE    
    @Path("{id}")
    public Response deleteRenter(@PathParam("id") int id)
    {
        return Response.ok().entity(this.deleteRenterDB(id)).build();
    }
    
    public Renter deleteRenterDB(int id){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            em.remove(renter);
            return renter;
        }
        else return null;
    }
    
    @POST
    @Path("{id}/rent")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postRenterRoom(@PathParam("id") int id,String jsonstring) throws JAXBException
    {
        Room room = this.unmarshalljsonRoom(jsonstring);
        return Response.ok().entity(this.assignRoomToRenter(id,room)).build();
    }
    
    public Renter assignRoomToRenter(int id,Room room)
    {
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            renter.setRoom(room);
            return renter;
        }
        else return null;
    }
    
    
    
    
}
