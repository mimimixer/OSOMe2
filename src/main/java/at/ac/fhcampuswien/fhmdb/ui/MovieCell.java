package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
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
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription() +"\n" + "Release year " + movie.getReleaseYear() + " Rated " + movie.getRating() + " Movie Length: " +movie.getLengthInMinutes() +" minutes"
                            : "No description available" +"\n" + "Release year " + movie.getReleaseYear() + " Rated " + movie.getRating()+  " Movie Length: " +movie.getLengthInMinutes() +" minutes"
             );

            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            String actors = String.join(", ", movie.getMainCast());
            String writersstr = String.join(", ", movie.getWriters());
            String directorsstr = String.join(", ", movie.getDirectors());
            people.setText("Main Cast: " + actors +"\n" +
                    "Writers: " + writersstr + "\n" +
                    "Directors: " + directorsstr);







            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            people.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

