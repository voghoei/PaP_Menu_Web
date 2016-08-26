package PaP.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import PaP.PaPException;
import PaP.model.ObjectModel;
import java.sql.*;
import PaP.model.Menu;

public class MenuManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public MenuManager(Connection conn, ObjectModel objectmodel) {
        this.objectModel = objectModel;
        this.conn = conn;
    }

    public void save(Menu menu) throws PaPException {

        long menuID;
        java.sql.Date insertDate = null;
        ResultSet rs = null;
        int inscnt;
        PreparedStatement stmt = null;

        String insertMenuSQL = "insert into menu(unit_id,beer_id,insertDate) values(?,?,?)";
        String updateMenuSQL = "update menu set unit_id=?, beer_id=?,insertDate=? where id = ?";
               
        
        try {
            if (!menu.isPersistent()) {
                stmt = (PreparedStatement) conn.prepareStatement(insertMenuSQL);
            } else {
                stmt = (PreparedStatement) conn.prepareStatement(updateMenuSQL);
            }
            
            stmt.setLong(1, menu.getUnit().getId());
            stmt.setLong(2, menu.getBeer().getId());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            
            
            if( menu.isPersistent() )
                stmt.setLong( 4, menu.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if (!menu.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if (stmt.execute(sql)) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        //
                        while (r.next()) {
                            // retrieve the last insert auto_increment value
                            menuID = r.getLong(1);
                            if (menuID > 0) {
                                menu.setId(menuID); // set this menu's db id (proxy object)
                            }
                        }
                    }
                } else {
                    throw new PaPException("AttributeManager.save: failed to save a Attribute");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("AttributeManager.save: failed to save a Attribute: " + e);
        }
    }

    public Iterator<Menu> restore(Menu menu) throws PaPException {
        String selectMenuSql = "select id, unit_id,beer_id,insertDate from menu";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectMenuSql);

        if (menu != null) {
            if (menu.getId() >= 0) {
                query.append(" where id=" + menu.getId());
            } else {
                if (menu.getUnit().getId() > 0 ) {
                    condition.append(" user_id = " + menu.getUnit().getId());
                }
                if (menu.getBeer().getId()>0) {
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
                    condition.append(" beer_id = " + menu.getBeer().getId());
                }
                if (menu.getInsertDate()!= null) {
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
                    condition.append(" insertDate = " + menu.getInsertDate());
                }
                if (condition.length() > 0) {
                    query.append(" where ");
                    query.append(condition);
                }
            }

        }
        try {
            stmt = conn.createStatement();
            if (stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                return new MenuIterator(r, objectModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaPException("MenuManager.restore: Could not restore persistent Menu object; Root cause: " + e);
        }
        throw new PaPException("MenuManager.restore: Could not restore persistent Menu object");

    }

    public void delete(Menu menu) throws PaPException {
        String deleteMenuSql = "delete from Menu where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if (!menu.isPersistent()) {
            return;
        }
        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteMenuSql);
            stmt.setLong(1, menu.getId());
            inscnt = stmt.executeUpdate();
            if (inscnt == 1) {
                return;
            } else {
                throw new PaPException("MenuManager.delete: failed to delete an menu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("MenuManager.delete: failed to delete an menu " + e);
        }
    }

}// MenuManager.java
