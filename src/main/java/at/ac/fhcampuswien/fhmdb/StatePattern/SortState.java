package at.ac.fhcampuswien.fhmdb.StatePattern;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public interface SortState {
    void sort(List<Movie> movies);

}
