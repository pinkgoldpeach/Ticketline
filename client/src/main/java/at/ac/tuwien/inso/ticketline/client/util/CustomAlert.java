package at.ac.tuwien.inso.ticketline.client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Helper class for throwing alertwindows
 */
public class CustomAlert {

    public static void throwErrorWindow(String message, String headerText, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);

        alert.setContentText(message);
        alert.setHeaderText(headerText);

        alert.showAndWait();
    }

    public static void throwWarningWindow(String message, String headerText, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(title);

        alert.setContentText(message);
        alert.setHeaderText(headerText);

        alert.showAndWait();
    }

    public static Optional<ButtonType> throwConfirmationWindow(String message, String headerText, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);

        alert.setContentText(message);
        alert.setHeaderText(headerText);

        return alert.showAndWait();
    }

    public static void throwInformationWindow(String message, String headerText, String title) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
