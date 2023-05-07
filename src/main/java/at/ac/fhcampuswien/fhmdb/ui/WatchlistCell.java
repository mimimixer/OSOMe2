package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

public class WatchlistCell extends ListCell<WatchlistMovieEntity> {

    private final Button showDetailsButton=new Button();

    //new
    private final Button addMovieToWatchlistBtn=new Button("Add to Watchlist");
    private final Button removeMovieFromWatchlistBtn=new Button("Remove from Watchlist");
    private final Button showWatchlistBtn=new Button("Show Watchlist");

    private boolean collapsedDetails = true;
    WatchlistRepository repository= new WatchlistRepository();


    //new
    public WatchlistCell() throws DatabaseException {//ClickEventHandler addToWatchlistClicked
        super();
        /*addMovieToWatchlistBtn.getStyleClass().add("btn"); //new
        addMovieToWatchlistBtn.setOnMouseClicked(mouseEvent -> {
            try {
                repository.addToWatchlist(getItem());
            } catch (SQLException e){
                System.out.println("Put Fehlermeldung into the UI, not here!");
              //  throw new DatabaseException(e); //new CustomException Data
            }
        });*/
    }
/*  Die Controller Klasse(n) fungieren als Schicht zwischen dem UI-Layer und dem Data-Layer.
    Folglich sind diese nur um jeweils eine Click-Funktion zu erweitern. So soll beim Klick auf
    „(Add to) Watchlist” ein Film in der Datenbank angelegt, bzw. beim Klick auf „Remove“ wieder
    aus der Datenbank entfernt werden. Dies soll mithilfe einer Lambda Expression umgesetzt werden,
    die vom Controller an die MovieCell Klasse übergeben wird. Schreibt dazu ein
    Functional Interface ClickEventHandler, welches eine Methode void onClick(T t) zur Verfügung
    stellt.
    Auszug MovieCell Klasse:
    public MovieCell(ClickEventHandler addToWatchlistClicked) {
    super();
    watchlistBtn.setOnMouseClicked(mouseEvent -> {
    addToWatchlistClicked.onClick(getItem());
    }); // ... rest of code
    }*/
}
