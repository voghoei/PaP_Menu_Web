package PaP.control;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import PaP.PaPException;
import PaP.model.*;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.sql.Connection;
import java.util.*;

/**
 *
 * @author reanimus
 */
public class ViewUserControl {

    private Connection conn = null;
    private ObjectModel objectModel = null;
    private Persistence persistence = null;
    private boolean hasError = false;
    private String error;

    public boolean hasError() {
        return this.hasError;
    }

    public String getError() {
        String err = null;
        if (this.hasError) {
            err = this.error;
            this.error = null;
            this.hasError = false;
        }
        return err;
    }

    private void connect() throws PaPException{
            this.close();
            this.conn = DbUtils.connect();
            this.objectModel = new ObjectModelImpl();
            this.persistence = new PersistenceImpl(conn,objectModel);
            this.objectModel.setPersistence(persistence);
        
    }
    private void close(){
        try{
            if(this.conn != null) {
                this.conn.close();
            }

        }catch (Exception e){
            System.err.println("Exception: "+e);
        }
    }

    public RegisteredUser getUserWithID(long id) {
        try {
            this.connect();
            RegisteredUser user = this.objectModel.createRegisteredUser();
            user.setId(id);
            Iterator<RegisteredUser> results = this.objectModel.findRegisteredUser(user);
            if (results.hasNext()) {
                return results.next();
            }else{
                this.hasError = true;
                this.error = "User doesn't exist.";
                return null;
            }
        }
        catch(PaPException e) {
            this.hasError = true;
            this.error = e.getMessage();
            return null;
        }
        finally {
            this.close();
        }
    }   

}
