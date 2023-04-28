package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import javafx.scene.control.ToggleButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistController {

    WatchlistRepository movieRepo;

    public void initialize() {
        System.out.println("WatchlistController initialized");

        movieRepo = new WatchlistRepository();
        List<WatchlistMovieEntity> watchlistMovieEntityList = new ArrayList<>();

        try {
            watchlistMovieEntityList = movieRepo.getAllMoviesFromWatchlist();
        } catch (SQLException e) {
            // throw new DatabaseException(e);
        }
        for (WatchlistMovieEntity chosen : watchlistMovieEntityList) {
            System.out.println(chosen);
        }

    }



    ClickEventHandler clickedItem;
    ToggleButton addRemoveFromWatchlistBtn=new ToggleButton();
    private final ClickEventHandler onAddToWatchlistClicked = () -> {
        // add code to add movie to watchlist here };
    };

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
}
