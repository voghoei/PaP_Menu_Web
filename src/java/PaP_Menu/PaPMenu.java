package pap_menu;

import PaP.persistence.impl.DbUtils;
import PaP.test.Insert;
import PaP.test.Delete;
import PaP.test.Update;
import java.sql.Connection;
import java.sql.Statement;

public class PaPMenu {

    public static void main(String[] args) {
        Connection conn = null;
        float result = 0;
        String query;
        
        // get a database connection
        try {
            conn = DbUtils.connect();

            Statement stmt = null;

            stmt = conn.createStatement();
            
            query = "delete from menu";
            stmt.executeUpdate(query);
            
            
            query = "delete from user";
            stmt.executeUpdate(query);
            
            query = "delete from beer";
            stmt.executeUpdate(query);
            
            query = "delete from unit";
            stmt.executeUpdate(query);

            

     
            

        } catch (Exception e) {      // just in case...
            System.err.println("CurrencyManager: Unable to obtain a database connection");
        
        } finally {
            // close the connection
            try {
                conn.close();
            } catch (Exception e) {
                System.err.println("Exception: " + e);
            }
        }

       Insert insert = new Insert();

       Update update = new Update();

      // Delete delete = new Delete();

    }

}
