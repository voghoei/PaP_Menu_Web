package PaP.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import PaP.PaPException;
import PaP.model.RegisteredUser;
import PaP.model.ObjectModel;

public class RegisteredUserIterator implements Iterator<RegisteredUser> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public RegisteredUserIterator(ResultSet rs, ObjectModel objectModel)
            throws PaPException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        } catch (Exception e) {	// just in case...
            throw new PaPException("RegisteredUserIterator: Cannot create RegisteredUser iterator; root cause: " + e);
        }
    }

    public boolean hasNext() {
        return more;
    }

    public RegisteredUser next() {
        long id;
        String UserName;
        String FirstName;
        String LastName;
        String State;
        String Address;
        String Password;
        String Email;
        String Phone;
        
        if (more) {

            try {
                id = rs.getLong(1);
                FirstName = rs.getString(2);
                LastName = rs.getString(3);
                UserName = rs.getString(4);
                Password = rs.getString(5);
                State = rs.getString(6);
                Address = rs.getString(7);       
                Email = rs.getString(8);
                Phone = rs.getString(9);
                

                more = rs.next();
            } catch (Exception e) {	// just in case...
                throw new NoSuchElementException("RegisteredUserIterator: No next RegisteredUser object; root cause: " + e);
            }

            RegisteredUser registeredUser = null;
            try {
                registeredUser = objectModel.createRegisteredUser(FirstName, LastName, UserName, Password, State, Address, Email, Phone);
                registeredUser.setPasswordDirect(Password);
                registeredUser.setId(id);

            } catch (PaPException ce) {
                ce.printStackTrace();
                System.out.println(ce);
            }
            return registeredUser;
        } else {
            throw new NoSuchElementException("RegisteredUserIterator: No next RegisteredUser object");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
