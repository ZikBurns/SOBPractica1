/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Renter;
import model.Requeriments;
import model.Room;
import model.TypeDimension;
import model.TypeLocation;
import model.TypeSex;
import services.RenterService;
import services.RoomService;

/**
 *
 * @author ZikBurns
 */
public class DataBaseTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("Service");
        EntityManager em = emf.createEntityManager();
        RoomService roomservice=new RoomService(em);
        RenterService renterservice = new RenterService(em);
        
        Renter renter = renterservice.createRenter("Norberta", "12345", TypeSex.UNISEX,48, false, false);
        System.out.println("--- Created  " + renter);
        
        Room room = roomservice.createRoomandRequirements("Habitació tranquila a les afores de sant pere i sant pau", "Carrer Gall d'hindi Nº3", "Sant Salvador", TypeDimension.SIMPLE, TypeLocation.INTERIOR, true, 90.67,"txell@gmail.com",987734132, TypeSex.UNISEX, 18, 70, false, false);
        System.out.println("--- Created  " + room);
        
        System.out.println("\n-----   ROOM TESTS   -----\n");
        System.out.println("--- General query\n"+roomservice.queryAllRooms("asc"));
        System.out.println("--- Ask for id=2 \n"+roomservice.queryRoomwithid(2));
        System.out.println("--- Renter id=9 has new requeriments\n"+""+roomservice.updateRoom(9, "Habitació tranquila a les afores de sant pere i sant pau", "Carrer Gall d'hindi Nº3", "Sant Salvador", TypeDimension.SIMPLE, TypeLocation.INTERIOR, true, 90.67,new Requeriments("newtxell@gmail.com",345653334, TypeSex.UNISEX, 18, 70, true, false)));
        System.out.println("--- Ask for id=9 \n"+roomservice.queryRoomwithid(9));
        System.out.println("--- Ask for id=1 \n"+roomservice.queryRoomwithid(1));
        System.out.println("--- Delete id=1 \n"+roomservice.deleteRoomDB(1));
        System.out.println("--- Ask for id=1 \n"+roomservice.queryRoomwithid(1));
        System.out.println("--- General query\n"+roomservice.queryRoomsfromCity("Tarragona","desc"));
        
        System.out.println("\n-----   RENTER TESTS   -----\n");
        System.out.println("--- General query\n"+renterservice.queryAllRenters());
        System.out.println("--- Ask for id=2 \n"+renterservice.queryRenterwithid(2));
        System.out.println("--- Renter with Id=2 uses a better password\n"+renterservice.updateRenter(2, "Joan", "D24sGsJtE7", TypeSex.MAN, 0, false, true));
        System.out.println("--- Ask for id=3 \n"+renterservice.queryRenterwithid(3));
        System.out.println("--- Renter with Id=3 has a room assigned\n"+renterservice.assignRoomToRenter(3, room));
        System.out.println("--- Ask for id=1 \n"+renterservice.queryRenterwithid(1));
        System.out.println("--- Delete id=1 \n"+renterservice.deleteRenterDB(1));
        System.out.println("--- Ask for id=1 \n"+renterservice.queryRenterwithid(1));
        System.out.println("--- General query\n"+renterservice.queryAllRenters());

        em.close();
        emf.close();
    }
}
