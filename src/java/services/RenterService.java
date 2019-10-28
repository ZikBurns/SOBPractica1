/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import model.Renter;
import model.Room;
import model.TypeSex;


public class RenterService {
    protected EntityManager em;
    
    public RenterService(EntityManager em){
        this.em=em;
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
     
    public Collection<Renter> queryAllRenters() {
        return (Collection<Renter>) em.createQuery(
                "SELECT r FROM Renter r").getResultList();
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
    
    public boolean updateRenter(int id,String user,String pass,TypeSex sex,int age, boolean smoker,boolean haspets){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            renter.setUsername(user);
            renter.setPassword(pass);
            renter.setSex(sex);
            renter.setAge(age);
            renter.setSmoker(smoker);
            renter.setHaspets(haspets);
            return true;
        }
        else return false;
        
    }
    
    public boolean deleteRenter(int id){
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            em.remove(renter);
            return true;
        }
        else return false;
    }
    
    public boolean assignRoomToRenter(int id,Room room)
    {
        Renter renter=queryRenterwithid(id);
        if(renter!=null){
            renter.setRoom(room);
            return true;
        }
        else return false;
    }
    
    
    
    
}
