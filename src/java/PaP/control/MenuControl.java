package PaP.control;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import PaP.PaPException;
import PaP.control.LoginControl;
import PaP.model.*;
import PaP.model.impl.ObjectModelImpl;
import PaP.persistence.Persistence;
import PaP.persistence.impl.DbUtils;
import PaP.persistence.impl.PersistenceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author reanimus
 */
public class MenuControl {

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

    public boolean attemptMenuCreate(long unitID, long beerID) {
        
            try {
                this.connect();
                RegisteredUser user = this.objectModel.createRegisteredUser();
                user.setId(userID);
                Iterator<RegisteredUser> results = this.objectModel.findRegisteredUser(user);
                if (results.hasNext()) {
                    user = results.next();
                    Bid bid = this.objectModel.createBid(auction, user, amount);
                    this.objectModel.storeBid(bid);
                    return true;
                }else{
                    this.hasError = true;
                    this.error = "User placing bid is invalid.";
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
    
    public ArrayList<Beer> getBeerForUnit(long unitId) {
        ArrayList<Beer> beerArray = new ArrayList<Beer>();
        try {
            this.connect();
            Unit modelunit = this.objectModel.createUnit();
            Unit unit = this.objectModel.createUnit();
            modelunit.setId(unitId);
           // modelunit.setRegisteredUser(user);
            Iterator<Unit> results = this.objectModel.findUnit(modelunit);
            HashMap<Long, Bid> bidMap = new HashMap<Long, Bid>();
            while (results.hasNext()) {
                modelunit = results.next();
                Long auctionID = new Long(model.getAuction().getId());
                if(bidMap.get(auctionID) != null) {
                    Bid candidate = bidMap.get(auctionID);
                    if(candidate.getAmount() < modelBeer.getAmount()) {
                        bidMap.put(auctionID, modelBeer);
                    }
                }else{
                    bidMap.put(auctionID, modelBeer);
                }
            }
            for(Bid bid : bidMap.values()) {
                beerArray.add(bid);
            }
            return beerArray;
        }
        catch(DTException e) {
            this.hasError = true;
            this.error = e.getMessage();
            return null;
        }
        finally {
            this.close();
        }
    }

}
