package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WatchlistController {

    @FXML
    public VBox box;

    @FXML
    public JFXListView watchlistView;



    @FXML
    public JFXButton returnBtn;

    WatchlistRepository movieRepo;

    ObservableList<Movie> observableMovies = FXCollections.observableArrayList();


    public void returnBtnClicked() throws IOException {
        loadHomeView();

    }

    public void loadHomeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 620);
            Stage stage = (Stage) box.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred.");
            alert.setContentText("Error while loading.");
        }
    }


    public void initialize() {

        System.out.println("WatchlistController initialized");


        movieRepo = new WatchlistRepository();
        List<WatchlistMovieEntity> watchlist;


        try {
            watchlist = movieRepo.getAllMoviesFromWatchlist();

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        observableMovies = (ObservableList<Movie>) watchlist.stream().map(WatchlistMovieEntity::watchlistEntityToMovie).toList();

        watchlistView.setItems(observableMovies);
        watchlistView.setCellFactory(listView -> new MovieCell(true));



    }

    public void initializeLayout(ObservableList<Movie>movies){
        //movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        //movieListView.setCellFactory(movieListView -> new MovieCell(false)); // apply custom cells to the listview

        watchlistView.setItems(movies);
        watchlistView.setCellFactory(watchlistView -> new MovieCell(true));

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

