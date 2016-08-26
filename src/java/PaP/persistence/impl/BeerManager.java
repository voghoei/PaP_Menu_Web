package PaP.persistence.impl;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import PaP.PaPException;
import PaP.model.Beer;
import PaP.model.ObjectModel;

public class BeerManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public BeerManager(Connection conn, ObjectModel objectModel) {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save(Beer beer) throws PaPException {
        String insertBeerSQL = "INSERT INTO beer (code, name, brand, type, abv, ibu, description) VALUES (?, ?, ?, ?,?,?,?)";
        String updateBeerSQL = "UPDATE beer SET code=?,name=?,brand=?,type=?,abv=?,ibu=?,description=? WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int inscnt;
        long beerId;
        
        try {
            // Check if we're creating or updating.
            if (!beer.isPersistent()) {
                stmt = (PreparedStatement) conn.prepareStatement(insertBeerSQL);
            } else {
                stmt = (PreparedStatement) conn.prepareStatement(updateBeerSQL);                
            }           
            if (beer.getCode() != null) {
                stmt.setString(1, beer.getCode());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.getName() != null) {
                stmt.setString(2, beer.getName());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }           

            
            if (beer.getBrand() != null) {
                stmt.setString(3, beer.getBrand());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.getType() != null) {
                stmt.setString(4, beer.getType());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.getABV() != 0) {
                stmt.setDouble(5, beer.getABV());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.getIBU() != 0) {
                stmt.setInt(6, beer.getIBU());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.getDesc() != null) {
                stmt.setString(7, beer.getDesc());
            } else {
                throw new PaPException("Beer.save: can't save a Beer: beer undefined");
            }  
            
            if (beer.isPersistent()) {
                stmt.setLong(8, beer.getId());
            }
            inscnt = stmt.executeUpdate();
            
            if (!beer.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if (stmt.execute(sql)) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while (r.next()) {

                            // retrieve the last insert auto_increment value
                            beerId = r.getLong(1);
                            if (beerId > 0) {
                                beer.setId(beerId); // set this person's db id (proxy object)                               

                            }
                        }
                    }
                } else {
                    throw new PaPException("BeerManager.save: failed to save a Beer");
                }
            } else {
                if (inscnt < 1) {
                    throw new PaPException("BeerManager.save: failed to save a Beer");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("BeerManager.save: failed to save a Beer: " + e);
        }
    }

    public Iterator<Beer> restore(Beer beer)
            throws PaPException {
        String selectBeerSql = "select id,code,name,brand,type,abv,ibu,description from beer";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectBeerSql);

        if (beer != null) {
            if (beer.getId() > 0) // id is unique, so it is sufficient to get a Beer
            {
                query.append(" where id = " + beer.getId());
            } else if (beer.getName() != null) // Title is unique, so it is sufficient to get a Beer
            {
                query.append(" where name = '" + beer.getName() + "'");
            } else {
                if (beer.getCode() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" code = '" + beer.getCode() + "'");
                    }
                if (beer.getBrand() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" brand = '" + beer.getBrand() + "'");
                    }
                if (beer.getType() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" type = '" + beer.getType() + "'");
                    }
                if (beer.getDesc() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" description = '" + beer.getDesc() + "'");
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
                return new BeerIterator(r, objectModel);
            }
        } catch (Exception e) {      // just in case...
            e.printStackTrace();
            throw new PaPException("BeerManager.restore: Could not restore persistent Beer object; Root cause: " + e);
        }

        throw new PaPException("BeerManager.restore: Could not restore persistent Beer object");
    }
    
    
    public void delete(Beer beer) throws PaPException {
        // We only delete with IDs.
        String deleteBeerSql = "DELETE FROM Beer WHERE id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if (!beer.isPersistent()) // is the object persistent?  If not, nothing to actually delete
        {
            return;
        }

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteBeerSql);
            stmt.setLong(1, beer.getId());
            inscnt = stmt.executeUpdate();
            if (inscnt == 1) {
                return;
            } else {
                throw new PaPException("BeerManager.delete: failed to delete an Beer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("BeerManager.delete: failed to delete an Beer: " + e);
        }
    }
}
