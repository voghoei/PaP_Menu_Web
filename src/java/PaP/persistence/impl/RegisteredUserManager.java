package PaP.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import PaP.PaPException;
import PaP.model.RegisteredUser;
import PaP.model.ObjectModel;
import java.sql.PreparedStatement;

public class RegisteredUserManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public RegisteredUserManager(Connection conn, ObjectModel objectModel) {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save(RegisteredUser registeredUser) throws PaPException {
        String insertRegisteredUserSql = "insert into user ( firstName, lastName, userName, password, state, address, email, phone ) values ( ?, ?, ?, ?, ?, ?,?,? )";
        String updateREgisteredUserSql = "update user set firstName = ?, lastName = ?, userName =?, password = ?, state=?, address=? ,email = ?, phone = ? where id = ?";
        PreparedStatement stmt;
        int inscnt;
        long registeredUserId;

        try {

            if (!registeredUser.isPersistent()) {
                stmt = (PreparedStatement) conn.prepareStatement(insertRegisteredUserSql);
            } else {
                stmt = (PreparedStatement) conn.prepareStatement(updateREgisteredUserSql);
            }

            

            if (registeredUser.getFirstName() != null) {
                stmt.setString(1, registeredUser.getFirstName());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: First Name undefined");
            }

            if (registeredUser.getLastName() != null) {
                stmt.setString(2, registeredUser.getLastName());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: Last Name undefined");
            }

            if (registeredUser.getUserName() != null) {
                stmt.setString(3, registeredUser.getUserName());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: UserName undefined");
            }
            
            if (registeredUser.getPassword() != null) {
                stmt.setString(4, registeredUser.getPassword());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: Password undefined");
            }

            if (registeredUser.getState() != null) {
                stmt.setString(5, registeredUser.getState());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: State undefined");
            }
            
            if (registeredUser.getAddress() != null) {
                stmt.setString(6, registeredUser.getAddress());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: Address undefined");
            }
            
            if (registeredUser.getEmail() != null) {
                stmt.setString(7, registeredUser.getEmail());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: Email undefined");
            }

            if (registeredUser.getPhone() != null) {
                stmt.setString(8, registeredUser.getPhone());
            } else {
                throw new PaPException("RegisteredUserManager.save: can't save a RegisteredUser: Phone undefined");
            }

            if (registeredUser.isPersistent()) {
                stmt.setLong(9, registeredUser.getId());
            }
            
            inscnt = stmt.executeUpdate();

            if (!registeredUser.isPersistent()) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if (inscnt == 1) {
                    String sql = "select last_insert_id()";
                    if (stmt.execute(sql)) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while (r.next()) {
                            // retrieve the last insert auto_increment value
                            registeredUserId = r.getLong(1);
                            if (registeredUserId > 0) {
                                registeredUser.setId(registeredUserId); // set this person's db id (proxy object)
                            }
                        }
                    }
                }
            } else {
                if (inscnt < 1) {
                    throw new PaPException("RegisteredUserManager.save: failed to save a RegisteredUser");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PaPException("RegisteredUserManager.save: failed to save a RegisteredUser: " + e);
        }
    }

    public Iterator<RegisteredUser> restore(RegisteredUser registeredUser)
            throws PaPException {
        String selectPersonSql = "select id, firstName, lastName, userName, password, state, address, email, phone from user";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        // form the query based on the given Person object instance
        query.append(selectPersonSql);

        if (registeredUser != null) {
            if (registeredUser.getId() >= 0) // id is unique, so it is sufficient to get a person
            {
                query.append(" where id = " + registeredUser.getId());
            } else {
                if (registeredUser.getUserName() != null) // userName is unique, so it is sufficient to get a person
                {
                    query.append(" where userName = '" + registeredUser.getUserName() + "'");

                    // Sometimes we're doing a login
                    if (registeredUser.getPassword() != null) {
                        query.append(" and password = '" + registeredUser.getPassword() + "'");
                    }
                } else {
                    if (registeredUser.getFirstName() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" firstName = '" + registeredUser.getFirstName() + "'");

                    }

                    if (registeredUser.getLastName() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" lastName = '" + registeredUser.getLastName() + "'");
                    }

                    if (registeredUser.getState() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" state = '" + registeredUser.getState() + "'");
                    }
                    
                    if (registeredUser.getAddress() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" address = '" + registeredUser.getAddress() + "'");
                    }
                    
                    
                    if (registeredUser.getEmail() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" email = '" + registeredUser.getEmail() + "'");
                    }

                    if (registeredUser.getPhone() != null) {
                        if (condition.length() > 0) {
                            condition.append(" and");
                        }
                        condition.append(" phone = '" + registeredUser.getPhone() + "'");
                    }

                    if (condition.length() > 0) {
                        query.append(" where ");
                        query.append(condition);
                    }
                }
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if (stmt.execute(query.toString())) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new RegisteredUserIterator(r, objectModel);
            }
        } catch (Exception e) {      // just in case...
            throw new PaPException("RegisteredUserManager.restore: Could not restore persistent RegisteredUser object; Root cause: " + e);
        }

        // if we get to this point, it's an error
        throw new PaPException("RegisteredUserManager.restore: Could not restore persistent RegisteredUser object");
    }

    public void delete(RegisteredUser registeredUser)
            throws PaPException {
        String deleteRegsiteredUserSql = "delete from user where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        // form the query based on the given Person object instance
        if (!registeredUser.isPersistent()) // is the Person object persistent?  If not, nothing to actually delete
        {
            return;
        }

        try {
            stmt = (PreparedStatement) conn.prepareStatement(deleteRegsiteredUserSql);

            stmt.setLong(1, registeredUser.getId());

            inscnt = stmt.executeUpdate();

            if (inscnt == 0) {
                throw new PaPException("RegisteredManager.delete: failed to delete this RegisteredUser");
            }
        } catch (SQLException e) {
            throw new PaPException("RegisteredUserManager.delete: failed to delete this RegisteredUser: " + e.getMessage());
        }
    }
}
