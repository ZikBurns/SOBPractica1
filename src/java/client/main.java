/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.*;
import services.*;
/**
 *
 * @author Pepe
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("RoomService");
        EntityManager em = emf.createEntityManager();
        RoomService roomservice=new RoomService(em);
        RenterService renterservice = new RenterService(em);
        
        em.getTransaction().begin();
        Renter renter = renterservice.createRenter("Norberta", "12345", TypeSex.UNISEX,48, false, false);
        em.getTransaction().commit();
        System.out.println("Created  " + renter);
        
        em.getTransaction().begin();
        Room room = roomservice.createRoomandRequirements("Habitació tranquila a les afores de sant pere i sant pau", "Carrer Gall d'hindi Nº3", "Tarragona", TypeDimension.SIMPLE, TypeLocation.INTERIOR, true, 90.67, TypeSex.UNISEX, 18, 70, false, false);
        em.getTransaction().commit();
        System.out.println("Created  " + room);
        em.close();
        emf.close();
    }
    
}
