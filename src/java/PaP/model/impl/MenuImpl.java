package PaP.model.impl;

import PaP.model.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class MenuImpl extends Persistent implements Menu {

    private Beer beer;
    private Unit unit;
    private Timestamp insertDate;
    

    public MenuImpl() {
    }

    public MenuImpl(Unit unit,Beer beer,  Timestamp insertDate) {
        this.beer = beer;
        this.unit = unit;
        this.insertDate = insertDate;
    }

    
    public Timestamp getInsertDate() {
        return insertDate;
    }

    
    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unitId) {
        this.unit = unitId;
    }
 @Override
    public String toString() {
        return "MenuImpl{" + "beer=  " + beer.toString() + ", unit=  " + unit.toString() + ", insertDate=" + insertDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuImpl other = (MenuImpl) obj;
        if (!Objects.equals(this.beer, other.beer)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }


   
}
