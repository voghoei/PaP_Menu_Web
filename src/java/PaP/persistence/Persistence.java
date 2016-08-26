package PaP.persistence;

import java.util.Iterator;
import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.RegisteredUser;
import PaP.model.Unit;
import PaP.model.Menu;

public interface Persistence {

    
    //Beer
    public void saveBeer(Beer beer) throws PaPException;

    public Iterator<Beer> restoreBeer(Beer beer) throws PaPException;

    public void deleteBeer(Beer beer) throws PaPException;

    // Unit
    public void saveUnit(Unit unit) throws PaPException;

    public Iterator<Unit> restoreUnit(Unit unit) throws PaPException;

    public void deleteUnit(Unit unit) throws PaPException;

    // RegisteredUser
    public void saveRegisteredUser(RegisteredUser registeredUser) throws PaPException;

    public Iterator<RegisteredUser> restoreRegisteredUser(RegisteredUser registeredUser) throws PaPException;

    public void deleteRegisteredUser(RegisteredUser registeredUser) throws PaPException;

    
    //Menu
    public void saveMenu(Menu menu) throws PaPException;

    public Iterator<Menu> restoreMenu(Menu menu) throws PaPException;

    public void deleteMenu(Menu menu) throws PaPException;
    
};
