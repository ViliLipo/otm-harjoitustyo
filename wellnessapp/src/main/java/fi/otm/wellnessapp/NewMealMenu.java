package fi.otm.wellnessapp;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NewMealMenu implements Initializable {

    @FXML
    private AnchorPane timePane;

    @FXML
    private TextField hourField;

    @FXML
    private TextField minuteField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button bigOKbutton;

    @FXML
    private AnchorPane foodPane;

    @FXML
    private ListView<?> foodView;

    @FXML
    private ComboBox<?> foodComBox;

    @FXML
    private TextField filterField;

    @FXML
    private Button addFoodButton;

    @FXML
    private TextField amountField;

    @FXML
    private Button continueButton;

    @FXML
    private Button removeFoodButton;
    
    private UiStructure us = UiStructure.getInstance();

    @FXML
    void addFood(ActionEvent event) {

    }

    @FXML
    void closeNewFoodWindow(ActionEvent event) {

    }

    @FXML
    void filterFood(ActionEvent event) {

    }

    @FXML
    void foodSelected(ActionEvent event) {

    }

    @FXML
    void removeFood(ActionEvent event) {

    }

    @FXML
    void selectDate(ActionEvent event) {

    }

    @FXML
    void swapPanes(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.foodPane.setVisible(false);
        this.timePane.setVisible(true);
        this.datePicker.setValue(LocalDate.now());
        
    }

}

