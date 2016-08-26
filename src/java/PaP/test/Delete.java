package PaP.test;

import PaP.PaPException;
import PaP.model.Menu;
import PaP.model.Unit;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.Iterator;

public class Delete {

    public Delete() {
        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;

        // get a database connection
        try {
            conn = DbUtils.connect();
        } catch (Exception seq) {
            System.err.println("ObjectModelDelete: Unable to obtain a database connection");
        }

        // obtain a reference to the ObjectModel module      
        objectModel = new ObjectModelImpl();

        // obtain a reference to Persistence module and connect it to the ObjectModel        
        persistence = new PersistenceImpl(conn, objectModel);

        // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence(persistence);

        try {

            //Delete the Running User object
            // First: find the Running User
            Iterator<RegisteredUser> userIter = null;
            RegisteredUser runningUser = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setFirstName("sahar");
            userIter = objectModel.findRegisteredUser(modelUser);
            while (userIter.hasNext()) {
                runningUser = userIter.next();
              //  System.out.println(runningUser);
            }

            

            // Delete the Running Unit object
            // First: find the Running Unit
            Unit runningUnit = null;
            Iterator<Unit> unitIter = null;
            Unit modelUnit = objectModel.createUnit();
            modelUnit.setCode("1111");
            unitIter = objectModel.findUnit(modelUnit);
            while (unitIter.hasNext()) {
                runningUnit = unitIter.next();
                System.out.println(runningUnit);
            }
            
            //Delete the Running Beer object
            // First: find the Running Beer
            Beer runningBeer = null;
            Iterator<Beer> beerIter = null;
            Beer modelBeer = objectModel.createBeer();
            modelBeer.setName("badwiser");
            beerIter = objectModel.findBeer(modelBeer);
            while (beerIter.hasNext()) {
                runningBeer = beerIter.next();
                System.out.println(runningBeer);
            }
            
            // Delete the Running Unit object
            // First: find the Running Unit
            Menu runningMenu = null;
            Iterator<Menu> menuIter = null;
            Menu modelMenu = objectModel.createMenu();
            modelMenu.setBeer(modelBeer);
            modelMenu.setUnit(modelUnit);
            menuIter = objectModel.findMenu(modelMenu);
            while (unitIter.hasNext()) {
                runningMenu = menuIter.next();
                System.out.println(runningMenu);
            }

            // Second: delete the Running Menu
            if (runningMenu != null) {
                objectModel.deleteMenu(runningMenu);
                System.out.println("Deleted the Running Menu");
            } else {
                System.out.println("Failed to retrieve the Running Menu object");
            }

            // Second: delete the Running Auction
            if (runningUnit != null) {
                objectModel.deleteUnit(runningUnit);
                System.out.println("Deleted the Running Unit");
            } else {
                System.out.println("Failed to retrieve the Running Unit object");
            }

            //Second: delete the Running Beer
            if (runningBeer != null) {
                objectModel.deleteBeer(runningBeer);
                System.out.println("Deleted the Running Beer");
            } else {
                System.out.println("Failed to retrieve the Running Beer object");
            }

            //Second: delete the Running User
            if (runningUser != null) {
                objectModel.deleteRegisteredUser(runningUser);
                System.out.println("Deleted the Running User");
            } else {
                System.out.println("Failed to retrieve the Running User object");
            }

        } catch (PaPException ce) {
            System.err.println("ClubsException: " + ce);
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        } finally {
            // close the connection
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e);
            }
        }
    }

}
