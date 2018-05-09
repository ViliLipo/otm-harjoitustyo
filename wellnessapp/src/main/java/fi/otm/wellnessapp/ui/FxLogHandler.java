/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.ui;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javafx.scene.control.Alert;

/**
 *
 * @author vili
 */
public class FxLogHandler extends Handler {

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().equals(Level.SEVERE)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Odottamaton Virhe");
            alert.setHeaderText("Voi juku!");
            alert.setContentText("Minestronessa olikin kärpänen !\n" + 
                    "Virheilmoitus: " + record.getThrown().getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

}
