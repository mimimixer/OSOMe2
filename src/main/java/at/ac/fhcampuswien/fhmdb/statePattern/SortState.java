
package at.ac.fhcampuswien.fhmdb.statePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.List;

public interface SortState {
    SortState performSort(ObservableList<Movie>  movieList);

}
