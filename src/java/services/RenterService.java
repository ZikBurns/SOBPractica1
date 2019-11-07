
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.StringReader;
import java.net.URI;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;


@Path("/tenant")
public class RenterService {
    protected EntityManager em;

    public RenterService() {
        em = Persistence.createEntityManagerFactory("Service")
                        .createEntityManager();
    }
    
    
    public RenterService(EntityManager em){
        this.em=em;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRenter(Renter renter, @Context UriInfo uriInfo) 
    {
        //List<Renter> renters = this.queryAllRenters();
        //if(renters.getx) return Response.status(Response.Status.CONFLICT).entity("Entity with ID: " + renter.getId()+" already exists").build();
        Renter newrenter=this.createRenter(renter.getUsername(), renter.getPassword(), renter.getSex(), renter.getAge(), renter.isSmoker(), renter.isHaspets());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(renter.getId()));
        return Response.created(builder.build()).entity(newrenter).build();
    }
    
     public Renter createRenter(String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        //List<Renter> list = queryAllRenters();
        //Renter last = list.get(list.size()-1);
        Renter renter = new Renter();
        //renter.setId(last.getId()+1);
        renter.setUsername(user);
        renter.setPassword(pass);
        renter.setSex(sex);
        renter.setAge(age);
        renter.setSmoker(smoker);
        renter.setHaspets(haspets);
        em.getTransaction().begin();
        em.persist(renter);
        em.getTransaction().commit();
        return renter;
    }
    
     
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getjsonAllRenters(){
        GenericEntity<List<Renter>> entity = new GenericEntity<List<Renter>>(queryAllRenters()) {};
         return Response.ok(entity).build();
    }
     
    public List<Renter> queryAllRenters() {
        return (List<Renter>) em.createQuery(
                "SELECT r FROM Renter r").getResultList();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRenterwithid(@PathParam("id") int id){
        Renter renter = this.queryRenterwithid(id);
        if (renter==null) return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        return Response.ok().entity(renter).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putUpdateRenter(@PathParam("id") int id, Renter renter)
    {
        renter=this.updateRenter(id,renter.getUsername(),renter.getPassword(),renter.getSex(),renter.getAge(),renter.isSmoker(),renter.isHaspets());
        if (renter==null) return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        else return Response.ok().entity(renter).build();
    }
    
    public Renter updateRenter(int id,String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            em.getTransaction().begin();
            renter.setUsername(user);
            renter.setPassword(pass);
            renter.setSex(sex);
            renter.setAge(age);
            renter.setSmoker(smoker);
            renter.setHaspets(haspets);
            em.getTransaction().commit();
            return renter;
        }
        else return null;
        
    }
    
    @DELETE    
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRenter(@PathParam("id") int id)
    {
        Renter renter =this.deleteRenterDB(id);
        if (renter==null) return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        else return Response.ok().entity(renter).build();
    }
    
    public Renter deleteRenterDB(int id){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            em.getTransaction().begin();
            em.remove(renter);
            em.getTransaction().commit();
            return renter;
        }
        else return null;
    }
    
    @POST
    @Path("{id}/rent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRenterRoom(@PathParam("id") int id, Room room) 
    {
        Renter renter=queryRenterwithid(id);
        //If Renter does not exist
        if (renter==null) return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        Room roomindb = em.find(Room.class, room.getId());
        //If renter exists, room exists but has a renter
        if(renter.getRoom()!=null) return Response.status(Response.Status.CONFLICT).entity("Entity with ID: " + renter.getId()+" already has a room").build();
        //If renter exists, room exists and has no renter
        //TODO This one is not working
        if((roomindb!=null)&&(roomindb.getRenter()==null)){
            em.getTransaction().begin();
            renter.setRoom(roomindb);
            roomindb.setRenter(renter);
            em.getTransaction().commit();
        }
        //If renter exists but room doesn't
        else if((roomindb==null)){
            em.getTransaction().begin();
            renter.setRoom(room);
            room.setRenter(renter);
            em.persist(room);
            em.getTransaction().commit();
        }
        return Response.ok().entity(renter).build();
    }
    
    public Renter assignRoomToRenter(int id,Room room)
    {
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            em.getTransaction().begin();
            renter.setRoom(room);
            room.setRenter(renter);
            em.persist(room);
            em.getTransaction().commit();
            return renter;
        }
        else return null;
    }
    
    
    
    
}
