package at.ac.fhcampuswien.fhmdb.StatePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public class NoneState implements SortState {
    @Override
    public void sort(List<Movie> movies) {
        // Do nothing, keep the movies in their original order
    }
}
