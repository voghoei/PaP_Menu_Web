package PaP.model;

public interface Beer extends Persistable {

    public String getCode();

    public void setCode(String code);
    
    public String getName();

    public void setName(String name);
    
    public String getBrand();

    public void setBrand(String brand);
    
    public double getABV();

    public void setABV(double abv);
    
    public int getIBU();

    public void setIBU(int ibu);

    public String getDesc();

    public void setDesc(String desc);
    
    public String getType();

    public void setType(String type);

}
