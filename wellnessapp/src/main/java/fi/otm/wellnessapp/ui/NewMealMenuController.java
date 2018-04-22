package fi.otm.wellnessapp.ui;

import fi.otm.wellnessapp.structure.WellnessService;
import fi.otm.wellnessapp.structure.FoodItemStructure;
import fi.otm.wellnessapp.dao.MealDao;
import fi.otm.wellnessapp.dao.MealDaoSqlite3;
import fi.otm.wellnessapp.structure.FoodItem;
import fi.otm.wellnessapp.structure.Meal;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewMealMenuController implements Initializable {

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
    private ListView<String> foodView;

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
    private FoodItem foodToAdd;
    private WellnessService ws;

    @FXML
    void addFood(ActionEvent event) {
        Double d = this.parseAmount();
        if (d != null) {
            this.ws.addFoodToNewMeal(foodToAdd, d);
            this.displayFoodItems();
        }

    }

    private void displayFoodItems() {
        this.foodView.getItems().clear();
        this.ws.getNewMeal().getFoodItems().entrySet().stream().forEach((Entry<FoodItem, Double> e) -> {
            String name = e.getKey().getName().split(",")[0];
            Double amount = e.getValue();
            this.foodView.getItems().add(String.format("%s : %.2fg", name, amount));
        });
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
        this.ws.addNewMealToUser();
        this.launchMainUi();

    }
        @FXML
    void onBackPressed(ActionEvent event) {
        this.ws.setNewMeal(null);
        this.launchMainUi();

    }

    private void launchMainUi() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            Stage theStage = (Stage) this.timePane.getScene().getWindow();
            theStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void filterFood(KeyEvent event) {
        System.out.println("filter food");
        String filter = this.filterField.getText();
        if (filter.contentEquals("")) {
            this.foodComBox.getItems().setAll(ws.getFis().getNameList());
            this.foodComBox.setValue(this.foodComBox.getItems().get(0));
        } else {
            this.foodComBox.getItems().setAll(ws.getFis().filteredNameList(filter));
            try {
                this.foodComBox.setValue(this.foodComBox.getItems().get(0));
            } catch (IndexOutOfBoundsException ex) {
                this.foodComBox.setPromptText("Ei osumia!");
            }

        }

    }

    @FXML
    void foodSelected(ActionEvent event) {
        this.foodToAdd = ws.getFis().getFoodItemByName(this.foodComBox.getValue());
        this.foodInfoField.setText(this.foodToAdd.info());

    }

    @FXML
    void removeFood(ActionEvent event) {
        String label = this.foodView.getSelectionModel().getSelectedItem();
        if (!(label == null || label.contentEquals(""))) {
            String foodName = label.split(" :")[0];
            this.ws.removeFoodItemFromNewMeal(foodName);
            this.displayFoodItems();
        }
    }

    @FXML
    void selectDate(ActionEvent event) {

    }

    @FXML
    void swapPanes(ActionEvent event) {
        if (this.parseTime() != null) {
            this.ws.setNewMeal(new Meal(this.parseTime(), ws.getUser().getUserId()));
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
            if (minutevalue < 60 && minutevalue >= 0 && hourvalue < 24 && hourvalue >= 0) {
                cl.add(Calendar.HOUR_OF_DAY, hourvalue);
                cl.add(Calendar.MINUTE, minutevalue);
                return cl.getTime();
            } else {
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ws = WellnessService.getInstance();
        this.datePicker.setValue(LocalDate.now());
        this.foodComBox.getItems().setAll(ws.getFis().getNameList());
        this.foodComBox.setValue(this.foodComBox.getItems().get(0));

    }

}
