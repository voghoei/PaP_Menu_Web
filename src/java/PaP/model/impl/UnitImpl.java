package PaP.model.impl;

import PaP.model.*;
import java.util.Objects;


public class UnitImpl extends Persistent implements Unit {

    private String title;
    private String code;
    private String desc;

    public UnitImpl() {
        
    }
    public UnitImpl(String title, String code, String desc) {
        this.title = title;
        this.code = code;
        this.desc = desc;

    }

    @Override
    public String toString() {
        return "VenueImpl{" + "title=" + title + ", code=" + code + ", desc=" + desc +'}';
    }

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
        final UnitImpl other = (UnitImpl) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    
    
}
