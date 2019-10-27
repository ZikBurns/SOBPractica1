/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.persistence.EntityManager;
import model.Renter;
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
    
}
