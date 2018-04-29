/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.ui;

/**
 *
 * @author vili
 */
import fi.otm.wellnessapp.structure.WellnessService;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserManagementController implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private Label userNameLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmField;

    @FXML
    private Button passwordChangeButton;

    @FXML
    private TextField calorieField;

    @FXML
    private Button okButton;

    @FXML
    private Label goalLabel;

    @FXML
    private Button backButton;

    @FXML
    void changePassword(ActionEvent event) {
        String password = this.newPasswordField.getText();
        String confrimation = this.confirmField.getText();
        if (!ws.updateUserPassword(password, confrimation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText("Salasanat eivät täsmää");
            alert.setContentText("Kirjoitathan salasanat huolellisesti kenttiin");
            alert.showAndWait();
            this.newPasswordField.setPromptText("uusi salasana");
            this.confirmField.setPromptText("vahvista salasana");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ilmoitus");
            alert.setHeaderText("Salasana vaihdettu");
            alert.setContentText("Salasana vaihdettu onnistuneesti");
            alert.showAndWait();
        }
    }
    private WellnessService ws;

    @FXML
    void goBack(ActionEvent event) {
        this.launchMainUi();
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
    void updateCaloriesAlternative(ActionEvent event) {
        this.updateCalorieCore();
    }

    @FXML
    void updateGoals(ActionEvent event) {
        this.updateCalorieCore();
    }

    private void updateCalorieCore() {
        String valstr = this.calorieField.getText();
        try {
            int value = Integer.parseInt(valstr);
            ws.updateUserCalorieGoal(value);
        } catch (NumberFormatException ex) {
            this.calorieField.setPromptText(("Anna tasaluku"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ws = WellnessService.getInstance();
    }

}
