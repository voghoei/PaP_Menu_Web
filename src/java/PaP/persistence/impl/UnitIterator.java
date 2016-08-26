package PaP.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import PaP.PaPException;
import PaP.model.Unit;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;

public class UnitIterator implements Iterator<Unit> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public UnitIterator(ResultSet rs, ObjectModel objectModel)
            throws PaPException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        } catch (Exception e) {	// just in case...
            throw new PaPException("UnitIterator: Cannot create Unit iterator; root cause: " + e);
        }
    }

    public boolean hasNext() {
        return more;
    }

    public Unit next() {
        
        long id;
        String Title;
        String Code;
        String Desc;
        Unit unit = null;
        if (more) {
            try {
                id = rs.getLong(1);
                Title = rs.getString(2);
                Code = rs.getString(3);
                Desc = rs.getString(4);
                more = rs.next();     
                unit = objectModel.createUnit(Title, Code, Desc);
                unit.setId(id);
            } catch (Exception e) {	// just in case...
                throw new NoSuchElementException("UnitIterator: No next Unit object; root cause: " + e);
            }
            return unit;
        } else {
            throw new NoSuchElementException("UnitIterator: No next Unit object");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
