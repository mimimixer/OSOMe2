package at.ac.fhcampuswien.fhmdb.statePattern;

import at.ac.fhcampuswien.fhmdb.enums.SortedState;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;
import at.ac.fhcampuswien.fhmdb.statePattern.*;

public class MovieSortState {
    public SortState actualObjectToBeSorted=new NoneState();;
    private SortedState currentStateOfSortingInformation;


    public MovieSortState() {
        this.actualObjectToBeSorted=actualObjectToBeSorted;
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

    public SortState sort(ObservableList<Movie> movieList) {
        actualObjectToBeSorted = actualObjectToBeSorted.performSort(movieList);
        return actualObjectToBeSorted;
    }
}
