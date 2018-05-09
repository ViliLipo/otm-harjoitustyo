/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.dao;

import fi.otm.wellnessapp.structure.User;

/**
 *
 * @author vili
 */
public interface UserDao {

    /**
     * Gets user that matches username and password hash. Sort of a login
     * method.
     *
     * @param username name of the user
     * @param passwordHash hashed password
     * @return User if match is found, else null.
     */
    public User getUser(String username, String passwordHash);

    /**
     * Adds given user to database.
     *
     * @param user User to add
     */
    public void addUser(User user);

    /**
     * Updates given user in database. Also sets userId field to match database.
     *
     * @param user User to update
     */
    public void updateUser(User user);

    /**
     * Removes given user from database.
     *
     * @param user User to remove
     */
    public void removeUser(User user);

}
