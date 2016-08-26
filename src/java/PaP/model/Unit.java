package PaP.model;

public interface Unit extends Persistable {

    String getTitle();

    void setTitle(String name);
    
    String getCode();

    void setCode(String code);
    
    String getDesc();

    void setDesc(String desc);

    
}