package PaP.model;

import PaP.PaPException;
import PaP.persistence.Persistence;
import java.util.Iterator;

public interface ObjectModel {

    public void setPersistence(Persistence persistence);

    // Unit
    public Unit createUnit(String title, String code, String desc) throws PaPException;   

    public Unit createUnit();

    public java.util.Iterator<Unit> findUnit(Unit modelUnit) throws PaPException;

    public void storeUnit(Unit unit) throws PaPException;

    public void deleteUnit(Unit unit) throws PaPException;
    
     public Unit getUnit(Menu menu) throws PaPException;
    

    
    //Beer
    public Beer createBeer(String code, String name, String brand, String type, double abv, int ibu, String desc) throws PaPException;

    public Beer createBeer();

    public java.util.Iterator<Beer> findBeer(Beer modelBeer) throws PaPException;

    public void storeBeer(Beer beer) throws PaPException;

    public void deleteBeer(Beer beer) throws PaPException;

    public Beer getBeer(Menu menu) throws PaPException;

    
    //Menu
    public Menu createMenu(Unit unit, Beer beer) throws PaPException;

    public Menu createMenu();

    public java.util.Iterator<Menu> findMenu(Menu modelMenu) throws PaPException;

    public void storeMenu(Menu menu) throws PaPException;

    public void deleteMenu(Menu menu) throws PaPException;

    public Menu getMenu(Unit unit) throws PaPException;
    
    public Menu getMenu(Beer beer) throws PaPException;


    //Registered User
    public RegisteredUser createRegisteredUser(String firstName, String lastName, String userName, String password, String state, String address, String email, String phone) throws PaPException;

    public RegisteredUser createRegisteredUser();

    public Iterator<RegisteredUser> findRegisteredUser(RegisteredUser registeredUser)throws PaPException;
    
    public void storeRegisteredUser(RegisteredUser registeredUser) throws PaPException;
    
    public void deleteRegisteredUser(RegisteredUser registeredUser) throws PaPException;

    
}
