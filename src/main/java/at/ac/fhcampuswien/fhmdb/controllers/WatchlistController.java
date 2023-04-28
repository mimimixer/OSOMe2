package at.ac.fhcampuswien.fhmdb.controllers;

import javafx.scene.control.ToggleButton;

public class WatchlistController {
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
