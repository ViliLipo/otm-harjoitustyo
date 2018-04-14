package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.dao.FoodItemStructure;
import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.Meal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private ComboBox<String> foodComBox;

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
    @FXML
    private TextArea foodInfoField;

    @FXML
    private Button backButton;

    private UiStructure us = UiStructure.getInstance();
    private Meal newMeal;
    private FoodItem foodToAdd;

    @FXML
    void addFood(ActionEvent event) {
        Double d = this.parseAmount();
        if (d != null) {
            this.newMeal.addFoodItem(foodToAdd, d);
        }

    }

    private Double parseAmount() {
        Double d = null;
        try {
            d = Double.parseDouble(this.amountField.getText());
        } catch (NumberFormatException ex) {
            this.amountField.clear();
            this.amountField.setPromptText("g");
        }
        return d;
    }

    @FXML
    void closeNewFoodWindow(ActionEvent event) {

    }

    @FXML
    void filterFood(KeyEvent event) {
        System.out.println("filter food");
        String filter = this.filterField.getText();
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(us.getDataBaseName());
        if (filter.contentEquals("")) {
            this.foodComBox.getItems().setAll(fis.getNameList());
            this.foodComBox.setValue(this.foodComBox.getItems().get(0));
        } else {
            this.foodComBox.getItems().setAll(fis.filteredNameList(filter));
            this.foodComBox.setValue(this.foodComBox.getItems().get(0));
        }

    }

    @FXML
    void foodSelected(ActionEvent event) {
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(us.getDataBaseName());
        this.foodToAdd = fis.getFoodItemByName(this.foodComBox.getValue());
        this.foodInfoField.setText(this.foodToAdd.info());

    }

    @FXML
    void removeFood(ActionEvent event) {

    }

    @FXML
    void selectDate(ActionEvent event) {

    }

    @FXML
    void swapPanes(ActionEvent event) {
        if (this.parseTime() != null) {
            this.newMeal = new Meal(this.parseTime(), us.getUser().getUserId());
            this.timePane.setVisible(false);
            this.foodPane.setVisible(true);
        } else {
            this.hourField.clear();
            this.minuteField.clear();
            this.hourField.setPromptText("HH");
            this.minuteField.setPromptText("mm");
        }
    }

    private Date parseTime() {
        Date d = new Date();
        Calendar cl = Calendar.getInstance();
        String hour = this.hourField.getText();
        String minute = this.minuteField.getText();
        if (minute.contentEquals("") || hour.contentEquals("")) {
            return null;
        }
        try {
            cl.setTime((asDate(this.datePicker.getValue())));
            int hourvalue = Integer.parseInt(hour);
            int minutevalue = Integer.parseInt(minute);
            cl.add(Calendar.HOUR_OF_DAY, hourvalue);
            cl.add(Calendar.MINUTE, minutevalue);
            return cl.getTime();

        } catch (NumberFormatException ex) {
            return null;
        }

    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(us.getDataBaseName());
        this.datePicker.setValue(LocalDate.now());
        this.foodComBox.getItems().setAll(fis.getNameList());
        this.foodComBox.setValue(this.foodComBox.getItems().get(0));
        

    }

}
