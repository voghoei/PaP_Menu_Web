package PaP.persistence.impl;

import java.sql.ResultSet;
import java.util.*;

import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.Unit;
import PaP.model.impl.BeerImpl;
import PaP.model.impl.MenuImpl;
import PaP.model.impl.UnitImpl;
import PaP.model.Menu;

public class MenuIterator implements Iterator<Menu> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public MenuIterator(ResultSet rs, ObjectModel objectModel)
            throws PaPException {

        this.rs = rs;
        this.objectModel = objectModel;

        try {
            more = rs.first();
        } catch (Exception e) {
            throw new PaPException("MenuIterator: Cannot create Menu iterator; root cause: " + e);
        }

    }

    public boolean hasNext() {
        return more;
    }

    public Menu next() {
        //result: id(long), item_id(long),minPrice(double),expiration(date),isClosed(boolean)
        Menu menu = new MenuImpl();

        if (more) {
            try {
                Unit user = new UnitImpl();
                Beer beer = new BeerImpl();
               
                menu.setId(rs.getLong(1));
                
                user.setId(rs.getLong(2));
                beer.setId(rs.getLong(3));
            
                java.sql.Timestamp timestamp = rs.getTimestamp(4);
                if(timestamp == null) {
                    throw new PaPException("MenuIterator: Failed to retrieve expiration from DB.");
                }
                menu.setInsertDate(new java.sql.Timestamp(timestamp.getTime()));
                more = rs.next();
                return menu;
            } catch (Exception e) {
                throw new NoSuchElementException("MenuIterator: No next Menu; Root Cause: " + e);
            }
        }
        //temp return statement
        return null;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

};
