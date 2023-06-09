package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UIAlert{

    public static void showErrorAlert(String message) {
        showAlert(AlertType.ERROR, "Error", message);
    }

    public static void showInfoAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("Something went wrong!");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles to the dialog pane

        //dialogPane.getStylesheets().add("path/to/styles.css");
        dialogPane.getStyleClass().add("ui-alert");
        dialogPane.setBackground(new Background(new BackgroundFill(Color.web("#FFC0CB"), null, null)));

        alert.showAndWait();
    }

    public static void showDoneAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("YAY");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles to the dialog pane

        //dialogPane.getStylesheets().add("path/to/styles.css");
        dialogPane.getStyleClass().add("ui-alert");
        dialogPane.setBackground(new Background(new BackgroundFill(Color.web("#93DFB8"), null, null)));

        alert.showAndWait();
    }


    public static void showWarningAlert(String message) {
        showAlert(AlertType.WARNING, "Warning", message);
    }

    public static void showConfirmationAlert(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("YAY");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles to the dialog pane

        //dialogPane.getStylesheets().add("path/to/styles.css");
        dialogPane.getStyleClass().add("ui-alert");
        dialogPane.setBackground(new Background(new BackgroundFill(Color.web("#FEDF00"), null, null)));

        alert.showAndWait();
    }


    public static void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("Information");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();

        // Apply styles to the dialog pane

        //dialogPane.getStylesheets().add("path/to/styles.css");
        dialogPane.getStyleClass().add("ui-alert");
        dialogPane.setBackground(new Background(new BackgroundFill(Color.web("#FFC0CB"), null, null)));

        alert.showAndWait();
    }
}
