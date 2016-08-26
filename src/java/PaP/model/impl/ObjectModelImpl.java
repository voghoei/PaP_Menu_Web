package PaP.model.impl;

import java.util.Iterator;

import PaP.PaPException;
import PaP.model.ObjectModel;
import PaP.persistence.Persistence;
import PaP.model.*;
import java.sql.Timestamp;


public class ObjectModelImpl extends Persistent implements ObjectModel {

    Persistence persistence = null;

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public ObjectModelImpl() {
        this.persistence = null;
    }

    public ObjectModelImpl(Persistence persistence) {
        this.persistence = persistence;
    }

    
    //Beer
    public Beer createBeer(String code, String name, String brand, String type, double abv, int ibu, String desc) {
        Beer newBeer = new BeerImpl(code, name, brand, type, abv, ibu, desc);
        return newBeer;
    }

    public Beer createBeer() {
        BeerImpl beerImpl = new BeerImpl();
        beerImpl.setId(-1);
        return beerImpl;
    }

    public Iterator<Beer> findBeer(Beer modelBeer) throws PaPException {
        return persistence.restoreBeer(modelBeer);
    }

    public void storeBeer(Beer beer) throws PaPException {
        persistence.saveBeer(beer);
    }

    public void deleteBeer(Beer beer) throws PaPException {
        persistence.deleteBeer(beer);
    }

    public Beer getBeer(Menu menu) throws PaPException {
        Beer modelBeer = this.createBeer();
        modelBeer.setId(menu.getBeer().getId());
        Iterator<Beer> results = persistence.restoreBeer(modelBeer);
        if (results.hasNext()) {
            return results.next();
        } else {
            throw new PaPException("Menu has invalid beer ID.");
        }
    }    

    //Unit -----------------------------------------------------------------------------------------------
    public Unit createUnit() {
        Unit unit = new UnitImpl();
        unit.setId(-1);
        return unit;
    }

    public Unit createUnit(String title, String code, String desc) throws PaPException {
        Unit unit = new UnitImpl(title, code, desc);
        return unit;
    }   
   
    public java.util.Iterator<Unit> findUnit(Unit modelUnit) throws PaPException {
        return persistence.restoreUnit(modelUnit);
    }

    public void storeUnit(Unit unit) throws PaPException {
        persistence.saveUnit(unit);
    }

    public void deleteUnit(Unit unit) throws PaPException {
        persistence.deleteUnit(unit);
    }
    
    public Unit getUnit(Menu menu) throws PaPException {
        Unit modelUnit = this.createUnit();
        modelUnit.setId(menu.getUnit().getId());
        Iterator<Unit> results = persistence.restoreUnit(modelUnit);
        if (results.hasNext()) {
            return results.next();
        } else {
            throw new PaPException("Menu has invalid Unit ID.");
        }
    }
    
    //RegisteredUser -----------------------------------------------------------------------------------------------
    public RegisteredUser createRegisteredUser() {
        RegisteredUser registeredUser = new RegisteredUserImpl();
        registeredUser.setId(-1);
        return registeredUser;
    }

    public RegisteredUser createRegisteredUser(String firstName, String lastName, String userName, String password, String state, String address, String email, String phone) throws PaPException {
        RegisteredUser registeredUser = new RegisteredUserImpl(firstName, lastName, userName, password, state, address, email, phone);
        return registeredUser;
    }

    public Iterator<RegisteredUser> findRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        return persistence.restoreRegisteredUser(registeredUser);
    }

    public void storeRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        persistence.saveRegisteredUser(registeredUser);
    }

    public void deleteRegisteredUser(RegisteredUser registeredUser) throws PaPException {
        persistence.deleteRegisteredUser(registeredUser);
    }
    
    
    //Menu ------------------------------------
    public Menu createMenu() {
        Menu menu = new MenuImpl();
        menu.setId(-1);
        return menu;
    }

    public Menu createMenu(Unit unit, Beer beer) {
        Menu menu = new MenuImpl(unit,beer,new Timestamp(System.currentTimeMillis()) );
        return menu;
    }

    public Iterator<Menu> findMenu(Menu modelMenu) throws PaPException {
        return persistence.restoreMenu(modelMenu);
    }

    public void storeMenu(Menu menu) throws PaPException {
        persistence.saveMenu(menu);
    }

    public void deleteMenu(Menu menu) throws PaPException {
        persistence.deleteMenu(menu);
    }
    
    public Menu getMenu(Beer beer) throws PaPException {
        Menu template = this.createMenu();
        template.setBeer(beer);
        Iterator<Menu> results = persistence.restoreMenu(template);
        if (results.hasNext()) {
            return results.next();
        } else {
            throw new PaPException("No menu found with this Beer ID.");
        }
    }
    
    public Menu getMenu(Unit unit) throws PaPException {
        Menu template = this.createMenu();
        template.setUnit(unit);
        Iterator<Menu> results = persistence.restoreMenu(template);
        if (results.hasNext()) {
            return results.next();
        } else {
            throw new PaPException("No menu found with this Beer ID.");
        }
    }

    
   
}
