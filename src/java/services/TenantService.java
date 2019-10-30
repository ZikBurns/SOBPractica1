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
import model.Tenant;
import model.Room;
import model.TypeSex;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
public class TenantService {
    protected EntityManager em;
    
    public TenantService(EntityManager em){
        this.em=em;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTenant(@FormParam("tenant") Tenant tenant) 
    {
        tenant=this.createTenant(tenant.getUsername(), tenant.getPassword(), tenant.getSex(), tenant.getAge(), tenant.isSmoker(), tenant.isHaspets());
        return Response.ok().entity(tenant).build();
    }
    
     public Tenant createTenant(String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Tenant tenant = new Tenant();
        tenant.setUsername(user);
        tenant.setPassword(pass);
        tenant.setSex(sex);
        tenant.setAge(age);
        tenant.setSmoker(smoker);
        tenant.setHaspets(haspets);
        em.persist(tenant);
        return tenant;
    }
    
     
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getjsonAllTenants(){
         return Response.ok().entity(this.queryAllTenants()).build();
    }
     
    public Collection<Tenant> queryAllTenants() {
        return (Collection<Tenant>) em.createQuery(
                "SELECT r FROM Tenant r").getResultList();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTenantwithid(@PathParam("id") int id){
        Tenant tenant = this.queryTenantwithid(id);
        if (tenant==null) return Response.noContent().build();
        else return Response.ok().entity(tenant).build();
    }
    
    public Tenant queryTenantwithid(int id){
        String query="SELECT r FROM Tenant r WHERE r.id = :id";
        try {
        return (Tenant) em.createQuery(query)
                        .setParameter("id", id)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /*TODO UpdateTenant as a PUT*/
    @PUT    
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putUpdateTenant(@PathParam("id") int id, Tenant tenant)
    {
        tenant=this.updateTenant(id,tenant.getUsername(),tenant.getPassword(),tenant.getSex(),tenant.getAge(),tenant.isSmoker(),tenant.isHaspets());
        if (tenant==null) return Response.noContent().build();
        else return Response.ok().entity(tenant).build();
    }
    
    public Tenant updateTenant(int id,String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Tenant tenant=queryTenantwithid(id);
        if(tenant!=null){
            tenant.setUsername(user);
            tenant.setPassword(pass);
            tenant.setSex(sex);
            tenant.setAge(age);
            tenant.setSmoker(smoker);
            tenant.setHaspets(haspets);
            return tenant;
        }
        else return null;
        
    }
    
    @DELETE    
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTenant(@PathParam("id") int id)
    {
        Tenant tenant =this.deleteTenantDB(id);
        if (tenant==null) return Response.noContent().build();
        else return Response.ok().entity(tenant).build();
    }
    
    public Tenant deleteTenantDB(int id){
        Tenant tenant=queryTenantwithid(id);
        if(tenant!=null){
            em.remove(tenant);
            return tenant;
        }
        else return null;
    }
    
    @POST
    @Path("{id}/rent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTenantRoom(@PathParam("id") int id,@FormParam("room") Room room) 
    {
        Tenant tenant=this.assignRoomToTenant(id,room);
        if (tenant==null) return Response.noContent().build();
        else return Response.ok().entity(tenant).build();
    }
    
    public Tenant assignRoomToTenant(int id,Room room)
    {
        Tenant tenant=queryTenantwithid(id);
        if(tenant!=null){
            tenant.setRoom(room);
            return tenant;
        }
        else return null;
    }
    
    
    
    
}
