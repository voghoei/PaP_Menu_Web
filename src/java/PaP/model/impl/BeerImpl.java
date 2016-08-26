package PaP.model.impl;

import PaP.model.*;
import java.util.Objects;


public class BeerImpl extends Persistent implements Beer {

    private String code;
    private String name;
    private String brand;
    private String type;
    private double abv=0;
    private int ibu=0;
    private String desc;
    

    public BeerImpl(){
    }

    public BeerImpl(String code, String name, String brand, String type, double abv, int ibu, String desc) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.abv = abv;
        this.ibu = ibu;
        this.desc = desc;
    }
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getABV() {
        return abv;
    }

    public void setABV(double abv) {
        this.abv = abv;
    }

    public int getIBU() {
        return ibu;
    }

    public void setIBU(int ibu) {
        this.ibu = ibu;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BeerImpl{" + "code=" + code + ", name=" + name + ", brand=" + brand + ", type=" + type + ", abv=" + abv + ", ibu=" + ibu + ", desc=" + desc + '}';
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
        final BeerImpl other = (BeerImpl) obj;
        if (Double.doubleToLongBits(this.abv) != Double.doubleToLongBits(other.abv)) {
            return false;
        }
        if (this.ibu != other.ibu) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }




}
