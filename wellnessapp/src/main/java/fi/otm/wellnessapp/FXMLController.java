package fi.otm.wellnessapp;

import fi.otm.wellnessapp.structure.Meal;
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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    private UiStructure us;
    @FXML
    private AnchorPane ap;
    @FXML
    private Canvas canvas;

    @FXML
    private Slider canvasSlider;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button newMealButton;

    @FXML
    private ListView<Meal> mealHistory;

    @FXML
    void historyClicked(MouseEvent event) {

    }

    @FXML
    void openUserMenu(MouseEvent event) {

    }

    @FXML
    void sliderDone(DragEvent event) {

    }

    @FXML
    void openMealMenu(ActionEvent event) {
        this.launchNewMealMenu();
    }

    private void launchNewMealMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/newMealMenu.fxml"));
            Scene scene = new Scene(root);
            Stage theStage = (Stage) this.ap.getScene().getWindow();
            theStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        us = UiStructure.getInstance();
        this.userNameLabel.setText(us.getUser().getUserName());
        this.mealHistory.getItems().setAll(us.getUser().getMealList());
    }
}
