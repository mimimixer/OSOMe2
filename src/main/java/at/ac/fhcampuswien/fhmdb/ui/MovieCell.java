package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label id = new Label();
    private final Label title = new Label();
     private final Label detail = new Label();
    private final Label genre = new Label();
    private final Label people = new Label();
    private final VBox layout = new VBox(title, detail ,people, genre);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
         //   setText("No Movies found for this parameters. \n Please change query.");
        } else {
            this.getStyleClass().add("movie-cell");
            id.setText("MovieID: " + movie.getId()+"              "+(movie.getImgUrl()));
            title.setText(movie.getTitle());
            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            allNumbers.setText("release year: "+ String.valueOf(movie.getReleaseYear())+"     Duration: "
                    +String.valueOf(movie.getLengthInMinutes())+"     Rating: "+String.valueOf(movie.getRating()));
            //     releaseYear.setText("release year: "+ String.valueOf(movie.getReleaseYear()));
            detail.setText(
                    movie.getDescription() != null

                            ? movie.getDescription() +"\n" + "Release year " + movie.getReleaseYear() + " Rated " + movie.getRating() + " Movie Length: " +movie.getLengthInMinutes() +" minutes"
                            : "No description available" +"\n" + "Release year " + movie.getReleaseYear() + " Rated " + movie.getRating()+  " Movie Length: " +movie.getLengthInMinutes() +" minutes"
             );
            
            String actors = String.join(", ", movie.getMainCast());
            String writersstr = String.join(", ", movie.getWriters());
            String directorsstr = String.join(", ", movie.getDirectors());
            people.setText("Main Cast: " + actors +"\n" +
                    "Writers: " + writersstr + "\n" +
                    "Directors: " + directorsstr);



            // color scheme
            id.getStyleClass().add("text-white");
            title.getStyleClass().add("text-yellow");
            allNumbers.getStyleClass().add("text-white");
        //releaseYear.getStyleClass().add("text-white");
        //    lengthInMinutes.getStyleClass().add("text-white");
            directors.getStyleClass().add("text-white");
            detail.getStyleClass().add("text-white");
        //    rating.getStyleClass().add("text-white");
            writers.getStyleClass().add("text-white");
            mainCast.getStyleClass().add("text-white");
        //    imgUrl.getStyleClass().add("text-black");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            people.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            id.fontProperty().set(id.getFont().font(10));
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 70);
            detail.setWrapText(true);
            detail.fontProperty().set(detail.getFont().font(15));
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(15);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

