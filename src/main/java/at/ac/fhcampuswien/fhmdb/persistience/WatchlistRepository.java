package at.ac.fhcampuswien.fhmdb.persistience;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.observePattern.Observable;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableEnum;
import at.ac.fhcampuswien.fhmdb.observePattern.ObservableUpdates;
import at.ac.fhcampuswien.fhmdb.observePattern.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository implements Observable { // die DAO-KLasse
                                  /* The Data Access Object (DAO) pattern is a structural pattern that allows us to
                                  isolate the application/business layer from the persistence layer (usually a relational
                                  database but could be any other persistence mechanism) using an abstract API.
                                    */
    //Declarations
    Dao<WatchlistMovieEntity, Long> movieEntityDao;
    private static WatchlistRepository repository;

    //+++++++++++++++++++++++++++++++++++++++++++++++++

    //Required Methods

    public void removeFromWatchlist(Movie movie) throws DatabaseException{
        //movieEntityDao.delete(chosenMovie(movie));
        try {
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = movieEntityDao.deleteBuilder();
            deleteBuilder.where().eq("apiID", movie.getApiID());
            deleteBuilder.delete();

        } catch (SQLException e) {
            throw new DatabaseException("unable to delete movie "+movie.getMovieTitle()+"from watchlist");
        }
    }

        //This is the Method List<WatchlistEntity>getAll() but we changed the name for better Comprehensiveness
    public List<WatchlistMovieEntity> getAllMoviesFromWatchlist() throws DatabaseException {
        try {
            return movieEntityDao.queryForAll();
        }catch (SQLException e){
            throw new DatabaseException("could not get the Watchlist ");
        }
    } //fehlt bei dieser Methode noch was? bitte überprüfen

    public void addToWatchlist(Movie movie) throws DatabaseException{
        String title = movie.getMovieTitle();
        try{
            movieEntityDao.create(chosenMovie(movie));
        }catch(SQLException e){
            throw new DatabaseException("add");
        }
        System.out.println("Added " + title + "to Watchlist");
    }

    /*
    methode angelegt, weil es steht in der Angabe. vermutlich ein copy-paste Fehler, da es Button bei einem Movie steht,
    und ein Movie eben zu einer WatchlistEntity transformiert werden soll, da nur letztere in die Database gespeichert werden können

    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try{
            movieEntityDao.create(chosenMovie(movie));
        }catch(SQLException e){
            throw new DatabaseException();
        }
    }
        */

    public WatchlistMovieEntity movieToEntity(Movie movie)
    {
        return new WatchlistMovieEntity(movie.getApiID(), movie.getMovieTitle(), movie.getDescription(), movie.getGenres(), movie.getReleaseYear(), movie.getRating(), movie.getLengthInMinutes(), movie.getImgUrl());
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Additional Methods and Constructors

    private WatchlistMovieEntity chosenMovie(Movie movie){
        return new WatchlistMovieEntity(movie);

    }
    /*das macht doch auch kein Sinn - ich krieg eine WLE erzeuge eine neue und schicke sie raus?
    private WatchlistMovieEntity chosenMovie(WatchlistMovieEntity movie){
        return new WatchlistMovieEntity();
    }
     */



    public static WatchlistRepository getInstance() throws DatabaseException{
        if (repository == null) {
            repository= new WatchlistRepository();
        }
        System.out.println(repository);
        return repository;
    }


    private WatchlistRepository() throws DatabaseException{
        this.movieEntityDao = Database.getDatabase().getMovieEntityDao();
    }



    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer))
        observers.add(observer);
    }
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers(ObservableUpdates observableUpdates) {
        for(Observer observer: observers){
            observer.update(observableUpdates);
        }
    }
}
