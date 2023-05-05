package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.customExceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.database.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.enums.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.enums.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.stream.DoubleStream;


public class HomeController implements Initializable {
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
    public JFXComboBox releaseYearComboBox=new JFXComboBox<>();
    @FXML
    public JFXComboBox ratingComboBox=new JFXComboBox<>();
   // @FXML
   // public JFXComboBox releaseYearComboBox;

  //  @FXML
   // public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;
    public List<Movie> allMovies;
    private static final String BASE = "http://prog2.fh-campuswien.ac.at/movies";

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected ObservableList<WatchlistMovieEntity> watchlistMovies = FXCollections.observableArrayList();
    public List<WatchlistMovieEntity> watchlistAll=new ArrayList<>();
    protected SortedState sortedState;

//START UI
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            System.out.println();
            System.out.println();
            System.out.println("what now");
        }
        initializeLayout();
    }

    //prepare lists  for UI
    public void initializeState() throws IOException {
     //   allMovies = Movie.initializeMovies();
        allMovies = MovieAPI.getAllMoviesDown(BASE);
      //  printMovies(allMovies);
      
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;

    //    watchlistMovies.clear();
     //   watchlistMovies.addAll(watchlistAll); // add all movies to the observable list
     //   sortedState = SortedState.NONE;
    }

    //SET meaning of BUTTONS in UI
    //SEARCH BUTTON
    public void sortMovies () {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getMovieTitle));
            sortedState = SortedState.ASCENDING;
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getMovieTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }
    //von Leon hinzugefügt
    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getMovieTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getMovieTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }
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
        applyFilters(searchQuery,genre,releaseYear,rating);
       // }
      //  applyAllFilters(searchQuery, genre);
      /*  if(sortedState != SortedState.NONE) {
            sortMovies();
        }
        korrigiert durch Leon */
        sortMovies(sortedState);

    }

    public void applyFilters(String searchQuery, Object genre, Object releaseYear, Object rating)throws NullPointerException {
        List<Movie> filteredMovies = allMovies;
        filterDescriptionByQuery(filteredMovies, searchQuery);
        List<Movie> finalSearchedList;

        if ((!searchQuery.isEmpty()) ||(genre != null && !genre.toString().equals("No filter"))||
                (releaseYear != null && !releaseYear.toString().equals("No filter"))||
                (rating != null && !rating.toString().equals("No filter"))){
            String genres=null, year=null, rates=null;
            if (genre!=null) {
                 genres = genre.toString();
            }
           if (releaseYear != null){
               year = releaseYear.toString();
           }
           if (rating!=null){
               rates = rating.toString();
           }
            filteredMovies = MovieAPI.getThatMovieListDown(searchQuery,genres,year,rates);

         /*   if (!searchQuery.isEmpty()){
                if((genre == null && !genre.toString().equals("No filter"))&&
                        (releaseYear == null && !releaseYear.toString().equals("No filter"))&&
                        (rating == null && !rating.toString().equals("No filter"))){
                             filteredMovies = Stream.of(filteredMovies,filterDescriptionByQuery(allMovies, searchQuery))
                                .flatMap(x->x.stream())
                                .distinct()
                                .collect(Collectors.toList());
                }else{
                            List<Movie> filteredMovies2 = MovieAPI.getThatMovieListDown(null,genres,year,rates);
                        filteredMovies=Stream.of(filteredMovies,filterDescriptionByQuery(filteredMovies2, searchQuery))
                                .flatMap(x->x.stream())
                                .distinct()
                                .collect(Collectors.toList());
                }
            }*/        }


        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }


    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell(false)); // apply custom cells to the listview

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

        countMoviesFrom(observableMovies, "Frank Capra");
        getMoviesBetweenYears(observableMovies, 1995, 2000);
        getLongestMovieTitle(observableMovies);
        getMostPopularActor(observableMovies);
    }
    //SORT BUTTON

        public void sortBtnClicked (ActionEvent actionEvent){
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

    //SORT method - still valid
    /* sort movies based on sortedState
     by default sorted state is NONE
     afterwards it switches between ascending and descending
     */

        //TESTPRINTER
        void printMovies (List < Movie > allMovies) {
            for (Movie m : allMovies) {
                System.out.println(m.getReleaseYear());
                System.out.println(m.ApiID());
                System.out.println(m.getImgUrl());
                System.out.println(m.getLengthInMinutes());
                System.out.println(m.getDirectors());
                System.out.println(m.getWriters());
                System.out.println(m.getMainCast());
                System.out.println(m.getRating());
            }
        }
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Java Streams
        public List<Integer> getAllExistingReleaseYears (List < Movie > movies) {
            List<Integer> releaseYears = movies.stream()
                    .map(Movie::getReleaseYear)
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            return releaseYears;
        }

        void methodToTryStreams () {
            countMoviesFrom(observableMovies, "Frank Capra");
            getMoviesBetweenYears(observableMovies, 1995, 2000);
            getLongestMovieTitle(observableMovies);
            getMostPopularActor(observableMovies);
        }

        public String getMostPopularActor (List < Movie > movies) {
            List<String> actors = movies.stream()
                    .map(movie -> movie.getMainCast())
                    .flatMap(movie -> movie.stream())
                    .collect(Collectors.toList());
            //8.4.23 https://www.geeksforgeeks.org/stream-flatmap-java-examples/ and
            // https://stackoverflow.com/questions/71051152/iterate-over-a-list-of-lists-and-check-for-a-condition-using-java-8
            Map<String, Long> actorsMap = actors.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));//8.4.23 https://www.techiedelight.com/count-frequency-elements-list-java/
            List<String> actor = actorsMap.entrySet().stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .stream().limit(1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
//OMG this was hard! 8.4.23
// https://www.tutorialspoint.com/java8/java8_streams.htm,
// https://www.swtestacademy.com/java-streams-comparators/,
// https://www.tutorialspoint.com/java-8-stream-terminal-operations
            System.out.println(actor.toString());
            return actor.toString();
        }
        public int getLongestMovieTitle (List < Movie > movies) {
            var result = movies.stream()
                    .mapToInt(movie -> movie.getMovieTitle().length())
                    .max()
                    .stream().limit(1)
                    .sum();
            System.out.println("the longest movie titel hast number of letters: " + result);
            return result;
        }
        public List<Movie> getMoviesBetweenYears (List < Movie > movies,int yearStart, int yearEnd){
            var result = movies.stream()
                    .filter(movie -> movie.getReleaseYear() > yearStart)
                    .filter(movie -> movie.getReleaseYear() < yearEnd)
                    .collect(Collectors.toList());
            System.out.println("the following movies were made between " + yearStart + " and " + yearEnd);
            printMovies(result);
            return result;
        }
        public long countMoviesFrom (List < Movie > movies, String director){
            var result = movies.stream()
                    .filter(movie -> movie.getDirectors().contains(director))
                    .count();
            System.out.println("there are " + result + " movies of director " + director + " in the movie list");
            return result;
        }
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //queryFilter method - for staticList
        public List<Movie> filterByQuery (List < Movie > movies, String query){
            if (query == null || query.isEmpty()) return movies;

            if (movies == null) {
                throw new IllegalArgumentException("movies must not be null");
            }

            return movies.stream()
                    .filter(Objects::nonNull)
                    .filter(movie ->
                            movie.getMovieTitle().toLowerCase().contains(query.toLowerCase()) ||
                                    movie.getDescription().toLowerCase().contains(query.toLowerCase())
                    )
                    .toList();
        }

        //genreFilter method - for staticList
        public List<Movie> filterByGenre (List < Movie > movies, Genre genre){
            if (genre == null) return movies;

            if (movies == null) {
                throw new IllegalArgumentException("movies must not be null");
            }

            return movies.stream()
                    .filter(Objects::nonNull)
                    .filter(movie -> movie.getGenres().contains(genre))
                    .toList();
        }

        //allFILTER methods query+genre - for staticList
        public void applyAllFilters (String searchQuery, Object genre){
            List<Movie> filteredMovies = allMovies;

            if (!searchQuery.isEmpty()) {
                filteredMovies = filterByQuery(filteredMovies, searchQuery);
            }

            if (genre != null && !genre.toString().equals("No filter")) {
                filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
            }

            observableMovies.clear();
            observableMovies.addAll(filteredMovies);
        }

        public void resetBtnClicked (ActionEvent actionEvent){
            allMovies = MovieAPI.getAllMoviesDown(BASE);
            observableMovies.clear();
            observableMovies.addAll(allMovies);

            sortBtn.setText("Sort");
            searchField.clear();
            movieIdField.clear();
            genreComboBox.setValue(null);
            ratingComboBox.setValue(null);
            releaseYearComboBox.setValue(null);


        }


        String getBusiestWriter (List < Movie > movies) {
            return movies.stream()
                    .flatMap(movie -> movie.getWriters().stream())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("");
        }

        public void showWatchlistBtnClicked(){

            loadWatchlistView();

        }


        public void loadWatchlistView() {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
                try{
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 620);
                    Stage stage = (Stage)box.getScene().getWindow();
                    stage.setScene(scene);

                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error has occurred.");
                    alert.setContentText("Error while loading.");
                }
            }

        }
