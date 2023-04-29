package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label apiID = new Label();
    private final Label title = new Label();
    private final Label movieDescription = new Label();

    private final Label moreInfo = new Label();
    private final Label genre = new Label();
    private final Label people = new Label();

    private Button detailsBtn = new Button("Show Details");
    private final Button addMovieToWatchlistBtn = new Button("Add to Watchlist");
    private VBox baseInfoBox = new VBox(title, movieDescription, genre);
    private final VBox buttonsBox = new VBox(detailsBtn, addMovieToWatchlistBtn);
    //private final VBox layout = new VBox(title, detail ,people, genre, showDetailsBtn, addMovieToWatchlistBtn);
    private HBox layout = new HBox(baseInfoBox, buttonsBox);
    private final VBox moreInfoBox = new VBox(moreInfo, people);
    private boolean collapsed = false;



    WatchlistRepository repository = new WatchlistRepository();

    public MovieCell() {//ClickEventHandler addToWatchlistClicked
        super();

        detailsBtn.getStyleClass().add("btn"); //new
        detailsBtn.setOnMouseClicked(mouseEvent -> {
            if(Objects.equals(detailsBtn.getText(), "Show Details")){
                detailsBtn.setText("Hide Details");
                baseInfoBox.getChildren().add(moreInfoBox);

            }else{
                detailsBtn.setText("Show Details");
                baseInfoBox.getChildren().remove(moreInfoBox);


            }

        });

        addMovieToWatchlistBtn.getStyleClass().add("btn"); //new
        addMovieToWatchlistBtn.setOnMouseClicked(mouseEvent -> {
            try {
                repository.addToWatchlist(getItem());
            } catch (SQLException e) {
                //throw new DatabaseException(e);
                System.out.println("Put Fehlermeldung into the UI, not here!");
                //  throw new DatabaseException(e); //new CustomException Data
            }
        });
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


            // color scheme
            apiID.getStyleClass().add("text-white");
            title.getStyleClass().add("text-yellow");
            addMovieToWatchlistBtn.getStyleClass().add("background-yellow");
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
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            apiID.fontProperty().set(apiID.getFont().font(10));
            title.fontProperty().set(title.getFont().font(20));
            movieDescription.setMaxWidth(this.getScene().getWidth() - 70);
            movieDescription.setWrapText(true);
            movieDescription.fontProperty().set(movieDescription.getFont().font(15));
            buttonsBox.setAlignment(Pos.TOP_RIGHT);
            detailsBtn.setPrefWidth(130);
            addMovieToWatchlistBtn.setPrefWidth(130);
            buttonsBox.spacingProperty().set(15);
            baseInfoBox.setPrefWidth(800);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(15);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            setGraphic(layout);



        }


    }
}

