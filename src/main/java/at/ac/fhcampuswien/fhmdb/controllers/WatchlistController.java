package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableEnum;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableUpdates;
import at.ac.fhcampuswien.fhmdb.observePattern.Observer;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.UIAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WatchlistController implements Observer {

    @FXML
    public VBox box;

    @FXML
    public JFXListView watchlistView;

private WatchlistController(){}
    private static  WatchlistController watchlistController;

    @FXML
    public JFXButton returnBtn;

    WatchlistRepository repository;

    ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private static Callback<Class<?>, Object> myFactory;
    public static void setControllerFactory(Callback<Class<?>, Object> factory) {
        myFactory = factory;
    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem)->{

        try{
            repository.removeFromWatchlist((Movie) clickedItem);
            repository.notifyObservers(new ObservableUpdates(((Movie) clickedItem).getMovieTitle(), ObservableEnum.REMOVED));
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
            fxmlLoader.setControllerFactory(myFactory);
            Parent root = FXMLLoader.load(fxmlLoader.getLocation());
            Scene scene = box.getScene();
            scene.setRoot(root);



        } catch (DatabaseException e){
            UIAlert.showErrorAlert("Error while deleting movie from watchlist");
        }
    };




    public void returnBtnClicked() throws IOException {
        loadHomeView();
    }

    public void loadHomeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory);
        try {
            Scene scene = new Scene(fxmlLoader.load(), 880, 620);
            Stage stage = (Stage) box.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred.");
            alert.setContentText("Error while loading.");
        }
    }


    public void initialize() throws DatabaseException {

        System.out.println("WatchlistController initialized");

        List<WatchlistMovieEntity> watchlist = new ArrayList<>();

        try {
            repository = WatchlistRepository.getInstance();
            repository.addObserver(this);
        } catch (DatabaseException e) {
            System.out.println();
            System.out.println();
            System.out.println("what now");
        }

        try {
            repository = WatchlistRepository.getInstance();
            watchlist = repository.getAllMoviesFromWatchlist();

        } catch (Exception e) {
            System.out.println("Some databaseError " + e.getMessage());
            UIAlert.showInfoAlert(" There is an error connecting to your saved movies. \n Check your connection. \n\n In the meantime you can look at a cat \n\n" +
                    "             /\\_/\\\n" + "            ( o.o )\n" + "            > ^ <");
        }

        ObservableList<Movie> movies = FXCollections.observableArrayList(
                watchlist.stream()
                        .map(WatchlistMovieEntity::watchlistEntityToMovie)
                        .collect(Collectors.toList())
        );

        initializeLayout(movies);
    }

    public void initializeLayout(ObservableList<Movie>movies) throws DatabaseException{
        //movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        //movieListView.setCellFactory(movieListView -> new MovieCell(false)); // apply custom cells to the listview
        try {
            watchlistView.setItems(movies);
            watchlistView.setCellFactory(watchlistView -> {

                    return new MovieCell(true,onAddToWatchlistClicked );

            });
        } catch (Exception e) {
            System.out.println("Error initializing watchlist layout" + e.getMessage());
            UIAlert.showInfoAlert("Error initializing watchlist layout");

            throw new DatabaseException("Error initializing watchlist layout", e);
        }

    }

    @Override
    public void update(ObservableUpdates observableUpdates) {
        if(observableUpdates.getObservableEnum()== ObservableEnum.REMOVED){
                String title = (observableUpdates.getData());
                UIAlert.showConfirmationAlert(title + " removed from watchlist");
        }
    }

    public static WatchlistController getInstance() throws DatabaseException{
        if (watchlistController == null) {
            watchlistController= new WatchlistController();
        }
        System.out.println(watchlistController);
        return watchlistController;
    }
}
    /*Die Controller Klasse(n) fungieren als Schicht zwischen dem UI-Layer und dem Data-Layer.
    Folglich sind diese nur um jeweils eine Click-Funktion zu erweitern. So soll beim Klick auf
    „(Add to) Watchlist” ein Film in der Datenbank angelegt, bzw. beim Klick auf „Remove“ wieder
    aus der Datenbank entfernt werden. Dies soll mithilfe einer Lambda Expression umgesetzt werden,
    die vom Controller an die MovieCell Klasse übergeben wird. Schreibt dazu ein
    Functional Interface ClickEventHandler, welches eine Methode void onClick(T t) zur Verfügung
    stellt.
    Auszug Controller:
    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) -> {
    // add code to add movie to watchlist here
    }; */

