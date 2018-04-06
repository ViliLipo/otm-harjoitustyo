package fi.otm.wellnessapp;

import fi.otm.wellnessapp.dao.FoodItemStructure;
import fi.otm.wellnessapp.dao.NutritionalComponentStructure;
import fi.otm.wellnessapp.dao.UserDao;
import fi.otm.wellnessapp.dao.UserDaoSqlite3;
import fi.otm.wellnessapp.structure.Meal;
import fi.otm.wellnessapp.structure.User;
import fi.otm.wellnessapp.tools.CsvParser;
import fi.otm.wellnessapp.tools.Sqlite3Utils;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
        // test runs
        String schemaFile = "src/main/resources/sqlite/dataBaseSchema.sqlite3";
        String dbName = "jdbc:sqlite:src/main/resources/sqlite/testDb.sqlite3";
        Sqlite3Utils.setupSchema(schemaFile, dbName);
        String csvName = "src/main/resources/csv/component.csv";
        CsvParser.convertComponentCSVtoSQLITE(csvName, dbName);
        String componentValueCsv = "src/main/resources/csv/component_value.csv";
        String foodNameFICsv = "src/main/resources/csv/foodname_FI.csv";
        CsvParser.convertFoodItems(componentValueCsv, foodNameFICsv, dbName);
        User u = new User("Matti", "Passoword252356");
        UserDao userDao = new UserDaoSqlite3(dbName);
        userDao.addUser(u);
        
        Meal meal = new Meal(new Date(), u.getUserId());
        u.addMeal(meal);
        FoodItemStructure fis = FoodItemStructure.getFoodItemStructure(dbName);
        NutritionalComponentStructure nc = NutritionalComponentStructure
                .getNutrititonalComponentStructure(dbName);
        meal.addFoodItem(fis.getFoodItemById(5), 200.d);
        
        System.out.println(meal.getTotalNutritionalValues().get(
        nc.getNutCompByName("ENERC")
        ));
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
