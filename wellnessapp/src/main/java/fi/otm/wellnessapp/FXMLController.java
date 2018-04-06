package fi.otm.wellnessapp;

import fi.otm.wellnessapp.dao.FoodItemStructure;
import fi.otm.wellnessapp.structure.FoodItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;

    @FXML
    private Label mealLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<String> foodCombo;

    @FXML
    private TextField filter;

    @FXML
    private TextField amountField;

    @FXML
    private Slider canvasSlider;

    @FXML
    private Label userNameLabel;

    @FXML
    void datePickerAction(ActionEvent event) {

    }

    @FXML
    void foodComboAction(ActionEvent event) {

    }

    @FXML
    void sliderDone(DragEvent event) {

    }

    @FXML
    void submitFood(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UiStructure us = UiStructure.getInstance();
        this.userNameLabel.setText(us.getUser().getUserName());
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(us.getDataBaseName());
        this.foodCombo.setItems(FXCollections.observableArrayList(fis.getNameList()));
    }    
}
