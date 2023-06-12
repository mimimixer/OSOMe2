package at.ac.fhcampuswien.fhmdb.statePattern;

import at.ac.fhcampuswien.fhmdb.enums.SortedState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;
import at.ac.fhcampuswien.fhmdb.statePattern.*;

public class MovieSortState {
    public SortState actualObjectToBeSorted;
    private SortedState currentStateOfSortingInformation;


    public MovieSortState() {
        actualObjectToBeSorted=new NoneState();
        currentStateOfSortingInformation = SortedState.NONE;
    }

    public void setSortState(SortState actualObjectAfterSortingSorted) {
        this.actualObjectToBeSorted = actualObjectAfterSortingSorted;
    }

    public SortState getSortState() {
        return actualObjectToBeSorted;
    }
    public SortedState getSortedState(){
        return currentStateOfSortingInformation;
    }

    public void sortMovies(ObservableList<Movie> movieList) {
        actualObjectToBeSorted.performSort(movieList);
    }
}
