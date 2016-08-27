package PaP.control;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import PaP.PaPException;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */
public class LoginControl {

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

    public boolean checkIsLoggedIn(HttpSession session)
            throws ServletException, IOException {
        
        // Get current session
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");

        if(currentUser != null)
        {
            Date now = new Date();
            Date last = (Date) session.getAttribute("currentSessionTimestamp");

            // 15 minute timeout
            if(now.getTime() - last.getTime() > 600000) {
                session.removeAttribute("currentSessionUser");
                session.removeAttribute("currentSessionTimestamp");
                return false;
            }
            return true;
        }
        return false;
    }
    
    public RegisteredUser getLoggedInUser(HttpSession session)
            throws ServletException, IOException {
        
        // Get current session
        RegisteredUser currentUser = (RegisteredUser) session.getAttribute("currentSessionUser");
        return currentUser;
    }

    public boolean attemptLogin(String username, String password, HttpSession session) {
        try {
            this.connect();

            Iterator<RegisteredUser> userIter = null;
            RegisteredUser runningUser = null;

            // Try
            RegisteredUser modelUser = this.objectModel.createRegisteredUser();
            modelUser.setUserName(username);
            modelUser.setPassword(password);

            // Fetch from DB
            userIter = this.objectModel.findRegisteredUser(modelUser);

            if (userIter.hasNext()) {
                runningUser = userIter.next();
                // Check for approval before access
                
                    session.setAttribute("currentSessionUser", runningUser);
                    session.setAttribute("currentSessionTimestamp", new Date());
                    return true;               
            } else {
                return false;
            }

        }
        catch(PaPException e) {
            this.hasError = true;
            this.error = e.getMessage();
            return false;
        }
        finally {
            this.close();
        }
    }

}
