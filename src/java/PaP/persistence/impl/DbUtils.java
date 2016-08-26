package PaP.persistence.impl;


import PaP.PaPException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtils {


    public static void disableAutoCommit( Connection conn ) 
            throws  PaPException 
    {
        try {
            conn.setAutoCommit(false);
        } 
        catch( SQLException ex ) {
            throw new  PaPException( "DbUtils.disableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    public static void enableAutoCommit( Connection conn ) 
            throws  PaPException 
    {
        try {
            conn.setAutoCommit(true);
        } 
        catch( SQLException ex ) {
            throw new  PaPException( "DbUtils.enableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    public static void commit(Connection conn) 
            throws  PaPException 
    {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new  PaPException( "DbUtils.commit: SQLException on commit " + ex.getMessage() );
        }
    }

    
    public static void rollback(Connection conn) 
            throws  PaPException 
    {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new  PaPException( "DbUtils.rollback: Unable to rollback transaction. " + ex.getMessage() );
        }
    }

    
    public static Connection connect() 
            throws  PaPException 
    {
        try {
            Class.forName(DbAccessConfig.DB_DRIVE_NAME);
        } 
        catch (Exception ex) {
            throw new  PaPException( "DbUtils.connect: Unable to find Driver" );
        }
        try {
            return DriverManager.getConnection( DbAccessConfig.DB_CONNECTION_URL,
                                                DbAccessConfig.DB_CONNECTION_USERNAME,
                                                DbAccessConfig.DB_CONNECTION_PWD );
        } 
        catch (Exception ex) {
            throw new  PaPException( "DbUtils.connect: Unable to connect to database " + ex.getMessage() );
        }
    }

}
