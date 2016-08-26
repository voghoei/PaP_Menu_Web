package PaP.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import com.mysql.jdbc.PreparedStatement;
import PaP.PaPException;
import PaP.model.Unit;
import PaP.model.ObjectModel;

public class UnitManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public UnitManager(Connection conn, ObjectModel objectModel) {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save(Unit unit)
            throws PaPException {
       
        
        String insertUnitSql = "insert into unit( title,code,description ) values ( ?, ?,? )";
        String updateUnitSql = "update unit set title = ? ,code =? , description=? where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long unitId;

        try {

            if (!unit.isPersistent()) {
                stmt = (PreparedStatement) conn.prepareStatement(insertUnitSql);
            } else {
                stmt = (PreparedStatement) conn.prepareStatement(updateUnitSql);
            }

            
            if (unit.getTitle() != null) // title is unique unique and non null
            {
                stmt.setString(1, unit.getTitle());
            } else {
                throw new PaPException("UnitManager.save: can't save a unit: Title undefined");
            }
            
            if (unit.getCode() != null) // title is unique unique and non null
            {
                stmt.setString(2, unit.getCode());
            } else {
                throw new PaPException("UnitManager.save: can't save a unit: Code undefined");
            }
            
            if (unit.getDesc() != null) // title is unique unique and non null
            {
                stmt.setString(3, unit.getDesc());
            } else {
                throw new PaPException("UnitManager.save: can't save a unit: Description undefined");
            }

            if (unit.isPersistent()) {
                stmt.setLong(4, unit.getId());
            }

            inscnt = stmt.executeUpdate();

            if (!unit.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if (stmt.execute(sql)) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while (r.next()) {

                            // retrieve the last insert auto_increment value
                            unitId = r.getLong(1);
                            if (unitId > 0) {
                                unit.setId(unitId); // set this person's db id (proxy object)                                

                            }
                        }
                    }
                } else {
                    throw new PaPException("UnitManager.save: failed to save a Unit");
                }
            } else {
                if (inscnt < 1) {
                    throw new PaPException("UnitManager.save: failed to save a Unit");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("UnitManager.save: failed to save a Unit: " + e);
        }
    }

    public Iterator<Unit> restore(Unit unit)
            throws PaPException {
        String selectUnitSql = "select id,title, code, description from unit";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectUnitSql);

        if (unit != null) {
            if (unit.getId() > 0) // id is unique, so it is sufficient to get a Unit
            {
                query.append(" where id = " + unit.getId());
            } else if (unit.getTitle() != null) // Title is unique, so it is sufficient to get a Unit
            {
                query.append(" where title = '" + unit.getTitle() + "'");
            } else {
                if (unit.getCode() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" code = '" + unit.getCode() + "'");
                    }
                
                if (unit.getDesc() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" description = '" + unit.getDesc() + "'");
                    }
                
                
                if (condition.length() > 0) {
                    query.append(" where ");
                    query.append(condition);
                }
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if (stmt.execute(query.toString())) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new UnitIterator(r, objectModel);
            }
        } catch (Exception e) {      // just in case...
            e.printStackTrace();
            throw new PaPException("UnitManager.restore: Could not restore persistent Unit object; Root cause: " + e);
        }

        throw new PaPException("UnitManager.restore: Could not restore persistent Unit object");
    }

    public void delete(Unit unit)
            throws PaPException {
        String deleteUnitSql = "delete from Unit where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if (!unit.isPersistent()) // is the Unit object persistent?  If not, nothing to actually delete
        {
            return;
        }

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteUnitSql);
            stmt.setLong(1, unit.getId());
            inscnt = stmt.executeUpdate();
            if (inscnt == 1) {
                return;
            } else {
                throw new PaPException("UnitManager.delete: failed to delete a Unit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("UnitManager.delete: failed to delete a Unit: " + e);
        }
    }
}
