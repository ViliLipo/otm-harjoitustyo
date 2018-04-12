/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp;

import fi.otm.wellnessapp.structure.User;

/**
 *
 * @author vili
 */
public class UiStructure {
    
    private static UiStructure singleton;
    private User user;
    private String dataBaseName;
    
    private LoginController loginController;
    private FXMLController main;
    private UiStructure() {
        
    }
    
    public static UiStructure getInstance() {
        if (singleton == null) {
            singleton = new UiStructure();
        }
        return singleton;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setLoginController(LoginController lc) {
        this.loginController = lc;
    }
    
    public void setFXMLController(FXMLController contro) {
        this.main = contro;
    }
    
    public LoginController getLoginController() {
        return this.loginController;
    }
    public void setDataBaseName(String name) {
        this.dataBaseName = name;
    } 
    
    public String getDataBaseName() {
        return this.dataBaseName;
    }
    
    
}
