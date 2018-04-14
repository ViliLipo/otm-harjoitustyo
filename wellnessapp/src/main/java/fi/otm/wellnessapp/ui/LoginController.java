/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.dao.UserDao;
import fi.otm.wellnessapp.dao.UserDaoSqlite3;
import fi.otm.wellnessapp.structure.User;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button submitButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private Button newUserButton;

    private UiStructure us;

    @FXML
    void newUser(ActionEvent event) {
        if (this.userNameField.getText().contentEquals("")
                || this.passwordField.getText().contentEquals("")) {
            this.userNameField.setPromptText("Give username and password");
        } else {
            String name = this.userNameField.getText();
            String password = this.passwordField.getText();
            User user = new User(name, User.md5Hash(password));
            UserDao userDao = new UserDaoSqlite3(us.getDataBaseName());
            userDao.addUser(user);
            us.setUser(user);
            this.launchMainUi();
        }

    }

    @FXML
    void submit(ActionEvent event) {
        if (!this.userNameField.getText().contentEquals("")
                && !this.passwordField.getText().contentEquals("")) {
            String userName = this.userNameField.getText();
            String password = this.passwordField.getText();
            UserDao userDao = new UserDaoSqlite3(us.getDataBaseName());
            User user = userDao.getUser(userName, User.md5Hash(password));
            if (user == null) {
                userNameField.clear();
                userNameField.setPromptText("Invalid credentials");
            } else {
                us.setUser(user);
                System.out.println(user.getDailyCalorieGoal());
                System.out.println("login succesful");
                this.launchMainUi();

            }
        }
        System.out.println("submit");

    }

    private void launchMainUi() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage theStage = (Stage) this.ap.getScene().getWindow();
            theStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void swapField(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.us = UiStructure.getInstance();
        this.us.setLoginController(this);
        Sqlite3Utils.initDb("src/main/resources/sqlite/dataBaseSchema.sqlite3",
                "jdbc:sqlite:src/main/resources/sqlite/appDb.sqlite3", "src/main/resources/csv/component.csv",
                "src/main/resources/csv/foodname_FI.csv", "src/main/resources/csv/component_value.csv");
    }

}