package PaP.model;

import java.sql.Timestamp;
import java.util.Date;

public interface Menu extends Persistable {

    Beer getBeer();

    void setBeer(Beer beer);
    
    Unit getUnit();

    void setUnit(Unit unit);
    
    Timestamp getInsertDate();

    void setInsertDate(Timestamp insertDate);
    
    
}
