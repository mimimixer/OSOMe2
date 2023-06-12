package at.ac.fhcampuswien.fhmdb.statePattern;

import at.ac.fhcampuswien.fhmdb.enums.SortedState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class DescendingState implements SortState{
    ObservableList<Movie> movieList;
    private SortedState sortedState;
    private SortState sortState;

    public DescendingState(ObservableList<Movie> movieList) {
        movieList.sort(Comparator.comparing(Movie::getMovieTitle).reversed());
    }
    public void setSortedState(SortedState sortedState) {
        this.sortedState = sortedState;
    }
    public void setSortState(SortState sortState) {
        this.sortState = sortState;
    }


    public SortedState getSortedState() {
        return sortedState;
    }
    @Override
    public SortState performSort(ObservableList<Movie> movieList) {
        return new AscendingState(movieList);
    }
}
