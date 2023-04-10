module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson; //6.4.23 https://stackoverflow.com/questions/72769462/failed-making-field-property-accessible-either-change-its-visibility-or-write
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
}