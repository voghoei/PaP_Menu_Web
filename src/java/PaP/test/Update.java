package PaP.test;

import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.Iterator;

public class Update {

    public Update() {
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

            // modify the name of the "Sahar" club to "Arash"
            // First: locate the Sahar club
            RegisteredUser saharUser = null;
            Iterator<RegisteredUser> userIter = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setLastName("voghoei");
            userIter = objectModel.findRegisteredUser(modelUser);
            while (userIter.hasNext()) {
                saharUser = userIter.next();
                System.out.println("sahar User is updated.  " + saharUser);
            }
            saharUser.setLastName("Ghasem");
            objectModel.storeRegisteredUser(saharUser);

            // modify the name of the "Sahar" club to "Arash"
            // First: locate the Sahar club
            Beer firtBeer = null;
            Iterator<Beer> itemIter = null;
            Beer modelBeer = objectModel.createBeer();
            modelBeer.setName("badwiser");
            itemIter = objectModel.findBeer(modelBeer);
            while (itemIter.hasNext()) {
                firtBeer = itemIter.next();
                System.out.println("sahar'item Beer is updated.  " + firtBeer);
            }            
            firtBeer.setName("B1");
            objectModel.storeBeer(firtBeer);

        } catch (PaPException ce) {
            System.err.println("Exception: " + ce);
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
