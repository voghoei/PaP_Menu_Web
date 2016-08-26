package PaP.test;

import PaP.PaPException;
import PaP.model.Unit;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.Date;
import PaP.model.Menu;
import PaP.model.Menu;

/**
 *
 * @author sahar
 */
public class Insert {

    public Insert() {

        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;

        RegisteredUser sahar;
        RegisteredUser daniel;
        Unit unit;
        Beer badwiser;
        Beer corola;
        Beer light;
        Menu menu;
       


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

            //   create a few user
            sahar = objectModel.createRegisteredUser("sahar", "voghoei", "sahr", "ss", "georgia","address","joe@mail.com", "23567895");
            objectModel.storeRegisteredUser(sahar);
            System.out.println("Entity objects User Sahar created and saved.");

            daniel = objectModel.createRegisteredUser("daniel", "last", "san", "di", "CA","Address2","sa@mail.com", "23567895");
            objectModel.storeRegisteredUser(daniel);
            System.out.println("Entity objects User Daniel created and saved.");

            //Unit
            unit = objectModel.createUnit("Unit1","1111","desc");
            objectModel.storeUnit(unit);
            System.out.println("Entity objects unit created and saved.");

            //Beer
            badwiser = objectModel.createBeer("Code1","badwiser","BW","t1",10.3,2,"desc");
            objectModel.storeBeer(badwiser);
            System.out.println("Entity objects badwiser created and saved.");
            
            corola = objectModel.createBeer("Code2","corola","cr","t2",7.23,4,"desc");
            objectModel.storeBeer(badwiser);
            System.out.println("Entity objects corola created and saved.");
            
            light = objectModel.createBeer("Code3","light","sl","t3",5,1,"desc");
            objectModel.storeBeer(light);
            System.out.println("Entity objects light created and saved.");
           
            //Menu
            menu = objectModel.createMenu(unit, badwiser);
            objectModel.storeMenu(menu);
            System.out.println("Entity objects Menu created and saved.");
            

        } catch (PaPException ce) {
            System.err.println("Exception: " + ce);
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the connection
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e);
                e.printStackTrace();
            }
        }
    }

}
