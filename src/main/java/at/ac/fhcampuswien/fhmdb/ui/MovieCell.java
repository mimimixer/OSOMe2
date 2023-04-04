package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label id = new Label();
    private final Label title = new Label();
    private final Label genre = new Label();
    private final Label releaseYear = new Label();
    private final Label detail = new Label();
    private final Label imgUrl = new Label();
    private final Label lengthInMinutes = new Label();
    private final Label directors = new Label();
    private final Label writers = new Label();
    private final Label mainCast = new Label();

    private final Label rating = new Label();

    private final VBox layout = new VBox(title, detail, genre);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            id.setText(movie.getId());
            title.setText(movie.getTitle());
            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            releaseYear.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            imgUrl.setText(movie.getTitle());
            lengthInMinutes.setText(movie.getTitle());
            String directors = movie.getDirectors()
                    .stream()
                    .collect(Collectors.joining(", "));
            String writers = movie.getWriters()
                    .stream()
                    .collect(Collectors.joining(", "));
            String mainCast = movie.getMainCast()
                    .stream()
                    .collect(Collectors.joining(", "));
            rating.setText(movie.getTitle());

            // color scheme
            id.getStyleClass().add("text-blue");
            title.getStyleClass().add("text-yellow");
            releaseYear.getStyleClass().add("text-white");
            lengthInMinutes.getStyleClass().add("text-white");
            detail.getStyleClass().add("text-white");
            imgUrl.getStyleClass().add("text-orange");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

