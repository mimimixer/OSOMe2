module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;
    requires com.h2database;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    //6.4.23 https://stackoverflow.com/questions/72769462/failed-making-field-property-accessible-either-change-its-visibility-or-write
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.enums;
    opens at.ac.fhcampuswien.fhmdb.enums to com.google.gson;
    exports at.ac.fhcampuswien.fhmdb.controllers;
    opens at.ac.fhcampuswien.fhmdb.controllers to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson, ormlite.jdbc;
    exports at.ac.fhcampuswien.fhmdb.database;
    opens at.ac.fhcampuswien.fhmdb.database to com.google.gson, ormlite.jdbc;
}