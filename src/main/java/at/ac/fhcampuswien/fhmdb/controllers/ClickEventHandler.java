package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;

import java.io.IOException;

public interface ClickEventHandler<T> {
    void onclick (T t) throws DatabaseException, IOException; //should be void onClick(T t)
}
