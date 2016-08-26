package PaP.model;


public interface RegisteredUser extends Persistable {

    
    String getUserName();

    void setUserName(String userName);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getState();

    void setState(String state);
    
    String getAddress();

    void setAddress(String adress);
    
    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);
    
    String getPassword();

    void setPassword(String password);
    void setPasswordDirect(String password);

    
}
