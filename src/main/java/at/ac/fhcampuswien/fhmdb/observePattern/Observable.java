package at.ac.fhcampuswien.fhmdb.observePattern;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    void addObserver(Observer observer);

    void removeObserver(Observer observer);


    void notifyObservers(ObservableUpdates observableUpdates);
}

