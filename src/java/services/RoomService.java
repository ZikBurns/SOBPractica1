/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.Path;
import model.*;

@Path("/books")
public class RoomService {
    protected EntityManager em;
    
    public RoomService(EntityManager em){
        this.em=em;
    }
    
    public Room createRoom(String description, String address, String city, 
                            TypeDimension dimension, TypeLocation location, 
                            boolean furniture, double price){
        Room room = new Room();
        room.setDescription(description);
        room.setAddress(address);
        room.setCity(city);
        room.setDimension(dimension);
        room.setLocation(location);
        room.setFurniture(furniture);
        room.setPrice(price);
        em.persist(room);
        return room;
    }
    
    public Room createRoomandRequirements(String description, String address, String city, 
                            TypeDimension dimension, TypeLocation location, 
                            boolean furniture, double price,String ownermail,int ownerphone,TypeSex sex,
                            int maxage,int minage,boolean smokers, boolean pets){
        Room room = new Room();
        room.setDescription(description);
        room.setAddress(address);
        room.setCity(city);
        room.setDimension(dimension);
        room.setLocation(location);
        room.setFurniture(furniture);
        room.setPrice(price);
        Requeriments req = new Requeriments();
        req.setSex(sex);
        req.setPets(pets);
        req.setSmokers(smokers);
        req.setMaxage(maxage);
        req.setMinage(minage);
        req.setOwnermail(ownermail);
        req.setOwnerphone(ownerphone);
        room.setReq(req);
        em.persist(room);
        return room;
    }
    
    //GET /rest/api/v1/room/${id}
    //Retorna la informació de l'habitació amb identificador ${id}.
        public Room queryIDtoInfo(int id) {
        String query = "SELECT * " +
                       "FROM Room e " +
                       "WHERE e.id = '" + id + "'";
        try {
            return (Room) em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
    public void removeRoom(Room id) {
        em.createQuery("Delete Room e " +
                       "WHERE e.id = :id ")
          .setParameter("id", id)
          .executeUpdate();
    }
    
    public long queryEmpSalary(String deptName, String empName) {
        String query = "SELECT e.salary " +
                       "FROM Employee e " +
                       "WHERE e.department.name = '" + deptName + "' AND " +
                       "      e.name = '" + empName + "'";
        try {
            return (Long) em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
    

    
    
}
