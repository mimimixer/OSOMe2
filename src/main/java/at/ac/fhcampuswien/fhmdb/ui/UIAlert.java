package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UIAlert{



    public static void showErrorAlert(String message) {
        showAlert(AlertType.ERROR, "Error", message);
    }

    public static void showInfoAlert(String message) {
        showAlert(AlertType.INFORMATION, "Information", message);
    }

    public static void showWarningAlert(String message) {
        showAlert(AlertType.WARNING, "Warning", message);
    }

    public static void showConfirmationAlert(String message) {
        showAlert(AlertType.CONFIRMATION, "Confirmation", message);
    }

    public static void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("Look out!");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles to the dialog pane

        //dialogPane.getStylesheets().add("path/to/styles.css");
        dialogPane.getStyleClass().add("ui-alert");



        alert.showAndWait();
    }
}
