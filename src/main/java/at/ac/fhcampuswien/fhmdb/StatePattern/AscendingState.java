package at.ac.fhcampuswien.fhmdb.StatePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class AscendingState implements SortState {
    @Override
    public void sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getMovieTitle));
    }
}
