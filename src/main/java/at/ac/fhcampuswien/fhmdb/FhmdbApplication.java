package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controllers.HomeController;
import at.ac.fhcampuswien.fhmdb.controllers.WatchlistController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import at.ac.fhcampuswien.fhmdb.creationalPatterns.ControllerFactory;
import javafx.util.Callback;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        Callback<Class<?>,Object> myFactory = new ControllerFactory();
        HomeController.setControllerFactory(myFactory);
        WatchlistController.setControllerFactory(myFactory);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory);

        Scene scene = new Scene(fxmlLoader.load(), 880, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();



/*try {
    Database.getDatabase().testDB();
}catch (SQLException e){
    e.printStackTrace();
}*/
    }
    public static void main(String[] args) {
           launch();


    }
}