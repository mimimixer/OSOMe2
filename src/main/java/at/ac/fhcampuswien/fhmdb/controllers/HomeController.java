package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.statePattern.AscendingState;
import at.ac.fhcampuswien.fhmdb.statePattern.DescendingState;
import at.ac.fhcampuswien.fhmdb.statePattern.NoneState;
import at.ac.fhcampuswien.fhmdb.statePattern.SortState;
import at.ac.fhcampuswien.fhmdb.statePattern.MovieSortState;

import at.ac.fhcampuswien.fhmdb.creationalPatterns.URLBuilder;
import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.customExceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableEnum;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableUpdates;
import at.ac.fhcampuswien.fhmdb.observePattern.Observer;
import at.ac.fhcampuswien.fhmdb.persistience.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.enums.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.UIAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import java.util.stream.DoubleStream;

import static at.ac.fhcampuswien.fhmdb.ui.UIAlert.showInfoAlert;


public class HomeController implements Observer {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public VBox box;

    @FXML
    public JFXButton resetBtn;
    @FXML
    public TextField searchField;

    @FXML
    public TextField movieIdField;
    @FXML
    public JFXListView movieListView;

    //public JFXListView watchListView;

    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public JFXComboBox releaseYearComboBox = new JFXComboBox<>();
    @FXML
    public JFXComboBox ratingComboBox = new JFXComboBox<>();
    @FXML
    public JFXButton sortBtn;
    public List<Movie> allMovies;
    String BASE = new URLBuilder().setHost(MovieAPI.getBASE()).setPath(MovieAPI.getPath()).build();
    public JFXButton showWatchlistBtn;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected ObservableList<WatchlistMovieEntity> watchlistMovies = FXCollections.observableArrayList();
    public List<WatchlistMovieEntity> watchlistAll = new ArrayList<>();
    private SortState sortState = new NoneState();
    public MovieSortState movieSortState=new MovieSortState();

    WatchlistRepository repository;
    private static Callback<Class<?>, Object> myFactory;
    public static void setControllerFactory(Callback<Class<?>, Object> factory) {
        myFactory = factory;
    }

    //Constructor
    private static HomeController homeController;

    public static HomeController getInstance(){
        if (homeController == null) {
            homeController= new HomeController();
        }
        System.out.println(homeController);
        return homeController;
    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) -> {

        //insert observePattern here..
        try {
            repository = WatchlistRepository.getInstance();
            repository.addToWatchlist((Movie) clickedItem);


            //UIAlert.showConfirmationAlert(((Movie) clickedItem).getMovieTitle()+" has been added to your watchlist.");

            repository.notifyObservers(new ObservableUpdates(
                    ((Movie) clickedItem).getMovieTitle(), ObservableEnum.ADDED));

        } catch (DatabaseException e) {
            repository.notifyObservers(new ObservableUpdates(
                    ((Movie) clickedItem).getMovieTitle(), ObservableEnum.EXISTS));


            String eMessage = e.getMessage();
            if (eMessage != null) {
                if (eMessage.equals("add")) {
                    System.out.println("Error while executing request: movie might already be on watchlist " + e.getMessage());
                    //UIAlert.showDoneAlert(((Movie) clickedItem).getMovieTitle() + " already on your watchlist!");
                } else {
                    System.out.println("Error while executing request: " + e.getMessage());
                    /*UIAlert.showInfoAlert(" There is an error connecting to your saved movies. \n Check your connection. \n\n In the meantime you can look at a cat \n\n" +
                            "             /\\_/\\\n" + "            ( o.o )\n" + "            > ^ <");*/
                }
            } else {
                System.out.println("Error while executing request: " + e.getMessage());
                /*UIAlert.showInfoAlert(" There is an error connecting to your saved movies. \n Check your connection. \n\n In the meantime you can look at a cat \n\n" +
                        "             /\\_/\\\n" + "            ( o.o )\n" + "            > ^ <");*/
            }
        }
    };

    //START UI
    public void initialize() {
        try {
            repository = WatchlistRepository.getInstance();
            repository.addObserver(this);
            System.out.println("Observerlist: ");
            System.out.println(WatchlistRepository.observers.toString());
        } catch (DatabaseException e) {
            System.out.println();
            System.out.println();
            System.out.println("what now");
        }


        try {
            initializeState();
        } catch (Exception e) {
            System.out.println();
            System.out.println();
            System.out.println("what now");
        }
        initializeLayout();
    }

    //prepare lists  for UI
    public void initializeState() throws DatabaseException {
        //   allMovies = Movie.initializeMovies();
        try {
            allMovies = MovieAPI.getAllMoviesDown(BASE);
        } catch (MovieApiException e) {
            System.out.println("Error while executing request: " + e.getMessage());
            ;
            UIAlert.showInfoAlert(" There is an error downloading the movie list. \n Check your internet connection. \n\n In the meantime we will show your saved movies");

            WatchlistRepository movieRepo = WatchlistRepository.getInstance();
            List<WatchlistMovieEntity> watchlist;
            try {
                watchlist = movieRepo.getAllMoviesFromWatchlist();
            } catch (DatabaseException ex) {
                showInfoAlert("cannto show your watchlist either" + ex.getMessage());
                throw new DatabaseException();
            }

            allMovies = watchlist.stream()
                    .map(WatchlistMovieEntity::watchlistEntityToMovie)
                    .collect(Collectors.toList()
                    );

        }
        //  printMovies(allMovies);

        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list

        //    watchlistMovies.clear();
        //   watchlistMovies.addAll(watchlistAll); // add all movies to the observable list
        //   sortedState = SortedState.NONE;

    }

    //SET meaning of BUTTONS in UI
    //SEARCH BUTTON
    public void sortMovies() {
        movieSortState.sort(observableMovies);
        System.out.println(movieSortState.actualObjectToBeSorted.getClass());

    }

    //von Leon hinzugefügt
    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending


    public void searchBtnClicked(ActionEvent actionEvent) {
        //   String apiID = movieIdField.getText().trim().toLowerCase();
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        Object releaseYear = releaseYearComboBox.getSelectionModel().getSelectedItem();
        Object rating = ratingComboBox.getSelectionModel().selectedItemProperty().getValue();
      /*  if (!apiID.equals("")){
            Movie movie = MovieAPI.getThatMovieSpecificDown(apiID);
            observableMovies.clear();
            observableMovies.add(movie);
        } else {*/
        applyFilters(searchQuery, genre, releaseYear, rating);
        sortMovies();
    }

    public void applyFilters(String searchQuery, Object genre, Object releaseYear, Object rating) {
        List<Movie> filteredMovies = allMovies;
        filterDescriptionByQuery(filteredMovies, searchQuery);
        List<Movie> finalSearchedList;

        if ((!searchQuery.isEmpty()) || (genre != null && !genre.toString().equals("No filter")) ||
                (releaseYear != null && !releaseYear.toString().equals("No filter")) ||
                (rating != null && !rating.toString().equals("No filter"))) {
            String genres = null, year = null, rates = null;
            if (genre != null) {
                genres = genre.toString();
            }
            if (releaseYear != null) {
                year = releaseYear.toString();
            }
            if (rating != null) {
                rates = rating.toString();
            }
            try {
                URLBuilder urlWithQuery = new URLBuilder();
                String url = urlWithQuery
                        .setHost(BASE)
                        // .setPath(path)
                        .setQuery(searchQuery)
                        .setGenre(genres)
                        .setReleaseYear(year)
                        .setRatingFrom(rates)
                        .build();
                // System.out.println(url);
                filteredMovies = MovieAPI.getAllMoviesDown(url);
            } catch (MovieApiException e) {
                showInfoAlert("Cannot apply filter right now, please check your connection");
            }
        }
        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
        sortMovies();
    }
    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> {
            return new MovieCell(false, onAddToWatchlistClicked);

        }); // apply custom cells to the listview

        //  watchListView.setItems(watchlistMovies);   // set the items of the listview to the observable list
        //  watchListView.setCellFactory(watchListView -> new WatchlistCell()); // apply custom cells to the listview

        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");


        releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.getItems().addAll(getAllExistingReleaseYears(observableMovies));
        releaseYearComboBox.setPromptText("Filter by Release Year");

        ratingComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        //  IntStream.rangeClosed(0, 9).forEach(ratingComboBox.getItems()::add);
        List<Double> values = DoubleStream.iterate(0, i -> i <= 9.5, i -> i + 0.5)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        ratingComboBox.getItems().addAll(values);
        ratingComboBox.setPromptText("Filter by Rating from .. up");

      /*  countMoviesFrom(observableMovies, "Frank Capra");
        getMoviesBetweenYears(observableMovies, 1995, 2000);
        getLongestMovieTitle(observableMovies);
        getMostPopularActor(observableMovies);*/
    }
    public void sortBtnClicked(ActionEvent actionEvent) {
        if (movieSortState.actualObjectToBeSorted instanceof NoneState){
            sortBtn.setText("Sort");
        } else if (movieSortState.actualObjectToBeSorted instanceof AscendingState) {
            sortBtn.setText("Sort (Ascending)");
        } else if (movieSortState.actualObjectToBeSorted instanceof DescendingState) {
            sortBtn.setText("Sort (Descending)");
        }
        sortMovies();
    }

    public List<Movie> filterDescriptionByQuery (List < Movie > movies, String query){
        if (query == null || query.isEmpty()) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }
    void printMovies (List < Movie > allMovies) {
        for (Movie m : allMovies) {
            System.out.println(m.getReleaseYear());
            System.out.println(m.getApiID());
            System.out.println(m.getImgUrl());
            System.out.println(m.getLengthInMinutes());
            System.out.println(m.getDirectors());
            System.out.println(m.getWriters());
            System.out.println(m.getMainCast());
            System.out.println(m.getRating());
        }
    }

    public List<Integer> getAllExistingReleaseYears (List < Movie > movies) {
        List<Integer> releaseYears = movies.stream()
                .map(Movie::getReleaseYear)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        return releaseYears;
    }

    public void resetBtnClicked (ActionEvent actionEvent) {
        sortBtn.setText("Sort");
        searchField.clear();
        //  movieIdField.clear();
        //   genreComboBox.getSelectionModel().clearSelection();
        genreComboBox.setValue(null);
        // ratingComboBox.getSelectionModel().clearSelection();
        ratingComboBox.setValue(null);
        //ratingComboBox.getItems().addAll();
        //   releaseYearComboBox.getSelectionModel().clearSelection();
        releaseYearComboBox.setValue(null);

        try {
            allMovies = MovieAPI.getAllMoviesDown(BASE);
        } catch (MovieApiException e) {
            System.out.println("Error while executing request: " + e.getMessage());

            //UIAlert.showInfoAlert(" There is an error downloading the movie list. \n Check your internet connection. \n\n In the meantime we will show your saved movies");

            observableMovies.clear();
            observableMovies.addAll(allMovies);
        }
    }

    public void showWatchlistBtnClicked(){
        loadWatchlistView();
    }

    public void loadWatchlistView() {
          /*  if (watchlistPage == null) {
                watchlistPage = (WatchlistController) controllerFactory.call(WatchlistController.class);
            }

           */
        // URL location = FhmdbApplication.class.getResource("/watchlist-view.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        //   System.out.println(location.toString());
        fxmlLoader.setLocation(FhmdbApplication.class.getResource("watchlist-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory);
        //  fxmlLoader.setController(watchlistPage);

        try{
            Scene scene = new Scene(fxmlLoader.load(), 880, 620);
            Stage stage = (Stage)box.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred.");
            alert.setContentText("Error while loading.");
        }
    }
    @Override
    public void update(ObservableUpdates update) {
        if (update.getObservableEnum() == ObservableEnum.ADDED){
            UIAlert.showConfirmationAlert( update.getData() + " successfully added to your watchlist.");
        }if (update.getObservableEnum() == ObservableEnum.EXISTS)
            UIAlert.showDoneAlert(update.getData() + " already exists in your watchlist");
        }
    }



