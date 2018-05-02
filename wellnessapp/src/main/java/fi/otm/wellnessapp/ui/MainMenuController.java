package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.structure.WellnessService;
import fi.otm.wellnessapp.structure.Meal;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private WellnessService ws;
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
    private Button removeButton;

    private DrawUtil du;

    @FXML
    void historyClicked(MouseEvent event) {
        try {
            Meal m = this.mealHistory.getSelectionModel().getSelectedItem();
            System.out.println(m);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ateria");
            alert.setHeaderText("Tietoja ateriasta");
            alert.setContentText(m.info());
            //System.out.println(m.info());
            alert.showAndWait();
        } catch (NullPointerException ex) {
            System.out.println("Nothing selected");
        }

    }

    @FXML
    void openUserMenu(MouseEvent event) {
        launchOtherScene("/fxml/UserManagement.fxml");
    }

    @FXML
    void sliderDone(MouseEvent event) {
        drawDiagram();
    }
    
    
    @FXML
    void sliderClick(MouseEvent event) {
        drawDiagram();
        //System.out.println("slider clicked");
    }

    @FXML
    void openMealMenu(ActionEvent event) {
        this.launchOtherScene("/fxml/NewMealMenu.fxml");
    }

    @FXML
    void removeMeal(ActionEvent event) {
        Meal m = this.mealHistory.getSelectionModel().getSelectedItem();
        if (m != null) {
            this.ws.removeMeal(m);
        }
        drawDiagram();
    }

    private void drawDiagram() {
        //System.out.println(this.canvasSlider.getValue());
        int dif = (int) Math.floor((100 - this.canvasSlider.getValue()) / 3);

        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.add(Calendar.DAY_OF_MONTH, -dif);
        //System.out.println(cl.getTime());
        du.drawDiagram(cl.getTime());
        this.userNameLabel.setText(ws.getUser().getUserName());
        this.mealHistory.getItems().setAll(ws.getUser().getMealList());
    }

    private void launchOtherScene(String resourcePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resourcePath));
            Scene scene = new Scene(root);
            Stage theStage = (Stage) this.ap.getScene().getWindow();
            theStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ws = WellnessService.getInstance();
        this.userNameLabel.setText(ws.getUser().getUserName());
        this.mealHistory.getItems().setAll(ws.getUser().getMealList());
        du = new DrawUtil(this.canvas, this.ws.getUser());
        du.drawDiagram(new Date());
    }
}
