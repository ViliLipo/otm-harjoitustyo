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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        UiStructure us = UiStructure.getInstance();
        us.setDataBaseName("jdbc:sqlite:src/main/resources/sqlite/appDb.sqlite3");
        stage.setTitle("WellnessApp");
        stage.setScene(scene);
        stage.show();
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