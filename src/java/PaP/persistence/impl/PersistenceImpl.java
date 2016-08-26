package PaP.persistence.impl;

import java.sql.Connection;
import java.util.Iterator;
import PaP.PaPException;
import PaP.model.Unit;
import PaP.model.Beer;
import PaP.model.ObjectModel;
import PaP.model.RegisteredUser;
import PaP.persistence.Persistence;
import PaP.model.Menu;

public class PersistenceImpl
        implements Persistence {

    private BeerManager beerManager = null;
    private RegisteredUserManager registeredUserManager = null;
    private UnitManager unitManager= null;
    private MenuManager menuManager = null;



    public PersistenceImpl(Connection conn, ObjectModel objectModel) {
        
        registeredUserManager = new RegisteredUserManager(conn, objectModel);
        beerManager = new BeerManager(conn, objectModel);
        unitManager = new UnitManager(conn, objectModel);
        menuManager = new MenuManager(conn, objectModel);
    }

    // Beer
    public void saveBeer(Beer beer) throws PaPException {
        beerManager.save(beer);
    }

    public Iterator<Beer> restoreBeer(Beer beer) throws PaPException {
        return beerManager.restore(beer);
    }

    public void deleteBeer(Beer beer) throws PaPException {
        beerManager.delete(beer);
    }

    
    //Unit
    public void saveUnit(Unit unit) throws PaPException {
        unitManager.save(unit);
    }

    public Iterator<Unit> restoreUnit(Unit unit) throws PaPException {
        return unitManager.restore(unit);
    }

    public void deleteUnit(Unit unit) throws PaPException {
        unitManager.delete(unit);
    }

    //User
    public void saveRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        registeredUserManager.save(registeredUser);
    }

    public Iterator<RegisteredUser> restoreRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        return registeredUserManager.restore(registeredUser);
    }

    public void deleteRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        registeredUserManager.delete(registeredUser);
    }

    
    //Menu
    public void saveMenu(Menu menu) throws PaPException {
        menuManager.save(menu);
    }

    public Iterator<Menu> restoreMenu(Menu menu) throws PaPException {
        return menuManager.restore(menu);
    }

    
    public void deleteMenu(Menu menu) throws PaPException {
        menuManager.delete(menu);
    }   

}
