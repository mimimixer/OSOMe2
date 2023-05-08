package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.controllers.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
//import at.ac.fhcampuswien.fhmdb.enums.WatchlistState;
import at.ac.fhcampuswien.fhmdb.enums.WatchlistState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label apiID = new Label();
    private final Label title = new Label();
    private final Label movieDescription = new Label();

    private final Label moreInfo = new Label();
    private final Label genre = new Label();
    private final Label people = new Label();
    private final Label imgURL = new Label();

    private Button detailsBtn = new Button("Show Details");
    private Button watchlistBtn = new Button("");
    private VBox baseInfoBox = new VBox(title, movieDescription, genre);
    private final VBox buttonsBox = new VBox(detailsBtn, watchlistBtn);
    //private final VBox layout = new VBox(title, detail ,people, genre, showDetailsBtn, addMovieToWatchlistBtn);
    private HBox layout = new HBox(baseInfoBox, buttonsBox);
    //private VBox moreInfoBox = new VBox(moreInfo);
    private WatchlistState watchlistState;

   // WatchlistRepository repository = new WatchlistRepository();

    private boolean hiddenDetails = true;
    private final boolean isWatchlistcell;





    public MovieCell(boolean isWatchlistcell, ClickEventHandler addToWatchListClicked) {//ClickEventHandler addToWatchlistClicked
        super();
        this.isWatchlistcell = isWatchlistcell;
   //     WatchlistRepository repository = new WatchlistRepository();

        detailsBtn.getStyleClass().add("btn"); //new
        detailsBtn.setOnMouseClicked(mouseEvent -> {
            System.out.println(watchlistState);
            if(hiddenDetails){
                hiddenDetails = false;
                detailsBtn.setText("Hide Details");
                if (!isWatchlistcell){
                    baseInfoBox.getChildren().add(moreInfo);
                    baseInfoBox.getChildren().add(people);
                    baseInfoBox.getChildren().add(imgURL);
                }else{
                    baseInfoBox.getChildren().add(moreInfo);
                    baseInfoBox.getChildren().add(imgURL);
                }

            }else{
                hiddenDetails = true;
                detailsBtn.setText("Show Details");
                baseInfoBox.getChildren().remove(moreInfo);
                baseInfoBox.getChildren().remove(people);
                baseInfoBox.getChildren().remove(imgURL);
            }

        });




       // watchlistBtn.setText(isWatchlistcell? "REMOVE FROM LIST" : "ADD TO LIST");

        watchlistBtn.setText(isWatchlistcell? "Remove" : "Add to List");

        watchlistBtn.setOnMouseClicked(mouseEvent-> {
            try {
                addToWatchListClicked.onclick(getItem());
            } catch (DatabaseException |IOException e) {
                System.out.println("Some databaseError " + e.getMessage());
                UIAlert.showInfoAlert(" There is an error connecting to your saved movies. \n Check your connection. \n\n In the meantime you can look at a cat \n\n" +
                        "             /\\_/\\\n" + "            ( o.o )\n" + "            > ^ <");
            }
        });


        /*
        watchlistBtn.setOnMouseClicked(mouseEvent -> {

            if(!isWatchlistcell) {

                try {
                    repository.addToWatchlist(getItem());
                    watchlistState = WatchlistState.ADDED;
                    UIAlert.showInfoAlert("Successfully added " + title.getText() +" to watchlist");



                } catch (DatabaseException e) {
                    //throw new DatabaseException(e);
                    UIAlert.showErrorAlert("Failed to add movie");
                    System.out.println("Put Fehlermeldung into the UI, not here!");
                    //  throw new DatabaseException(e); //new CustomException Data
                }
            }

            else {
                try{
                    repository.removeFromWatchlist(getItem());
                    FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
                    Parent root = FXMLLoader.load(fxmlLoader.getLocation());
                    Scene scene = watchlistBtn.getScene();
                    scene.setRoot(root);
                    watchlistState = WatchlistState.NONE;

                } catch (DatabaseException | IOException e) {
                    UIAlert.showErrorAlert("Failed to add movie");
                    System.out.println("Put Fehlermeldung into the UI, not here!");
                }
            }

        });*/
    }


    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);


        if (empty || movie == null) {
            setGraphic(null);
            //   setText("No Movies found for this parameters. \n Please change query.");
        } else {
            this.getStyleClass().add("movie-cell");
            //          id.setText("MovieID: " + movie.getId()+"              "+(movie.getImgUrl()));
            title.setText(movie.getMovieTitle());
            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            //    allNumbers.setText("release year: "+ String.valueOf(movie.getReleaseYear())+"     Duration: "
            //            +String.valueOf(movie.getLengthInMinutes())+"     Rating: "+String.valueOf(movie.getRating()));
            //     releaseYear.setText("release year: "+ String.valueOf(movie.getReleaseYear()));
            movieDescription.setText(
                    movie.getDescription() != null

                            ? movie.getDescription()
                            : "No description available");

            moreInfo.setText("Release year " + movie.getReleaseYear() + " Rated " + movie.getRating() + " Movie Length: " + movie.getLengthInMinutes() + " minutes");

            String actors = String.join(", ", movie.getMainCast());
            String writersstr = String.join(", ", movie.getWriters());
            String directorsstr = String.join(", ", movie.getDirectors());
            people.setText("Main Cast: " + actors + "\n" +
                    "Writers: " + writersstr + "\n" +
                    "Directors: " + directorsstr);
            imgURL.setText(movie.getImgUrl());


            // color scheme
            apiID.getStyleClass().add("text-white");
            title.getStyleClass().add("text-yellow");
            watchlistBtn.getStyleClass().add("background-yellow");
            detailsBtn.getStyleClass().add("background-yellow");
            //     allNumbers.getStyleClass().add("text-white");
            //releaseYear.getStyleClass().add("text-white");
            //    lengthInMinutes.getStyleClass().add("text-white");
            //     directors.getStyleClass().add("text-white");
            movieDescription.getStyleClass().add("text-white");
            //    rating.getStyleClass().add("text-white");
            //   writers.getStyleClass().add("text-white");
            //   mainCast.getStyleClass().add("text-white");
            //    imgUrl.getStyleClass().add("text-black");
            moreInfo.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            people.getStyleClass().add("text-white");
            imgURL.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            apiID.fontProperty().set(apiID.getFont().font(10));
            title.fontProperty().set(title.getFont().font(20));
            movieDescription.setMaxWidth(this.getScene().getWidth() - 70);
            movieDescription.setWrapText(true);
            movieDescription.fontProperty().set(movieDescription.getFont().font(15));
            buttonsBox.setAlignment(Pos.TOP_RIGHT);
            detailsBtn.setPrefWidth(100);
            watchlistBtn.setPrefWidth(100);
            buttonsBox.spacingProperty().set(15);
            baseInfoBox.setPrefWidth(720);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(15);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            setGraphic(layout);


        }


    }

}

