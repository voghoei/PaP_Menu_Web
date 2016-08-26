package PaP.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.ObjectModel;


public class BeerIterator implements Iterator<Beer> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public BeerIterator(ResultSet rs, ObjectModel objectModel)
            throws PaPException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        } catch (Exception e) {	// just in case...
            throw new PaPException("BeerIterator: Cannot create Beer iterator; root cause: " + e);
        }
    }

    public boolean hasNext() {
        return more;
    }

    public Beer next() {
        
        long id;
        String Name;
        String Code;
        String Brand;
        String Type;
        double ABV;
        int IBU;
        String Desc;
        Beer beer = null;
        if (more) {
            try {
                id = rs.getLong(1);                
                Code = rs.getString(2);
                Name = rs.getString(3);
                Brand = rs.getString(4);
                Type = rs.getString(5);
                ABV = rs.getDouble(6);
                IBU = rs.getInt(7);
                Desc = rs.getString(8);
                more = rs.next();     
                beer = objectModel.createBeer(Code,Name,Brand,Type,ABV,IBU,Desc);
                beer.setId(id);
            } catch (Exception e) {	// just in case...
                throw new NoSuchElementException("BeerIterator: No next Beer object; root cause: " + e);
            }
            return beer;
        } else {
            throw new NoSuchElementException("BeerIterator: No next Beer object");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
