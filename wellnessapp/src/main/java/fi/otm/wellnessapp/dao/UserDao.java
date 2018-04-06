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
    
        
    public User getUser(String username, String passwordHash);
    
    public void addUser(User user);
    
    public void updateUser(User user);
    
    public void removeUser(User user);
    
    
}
