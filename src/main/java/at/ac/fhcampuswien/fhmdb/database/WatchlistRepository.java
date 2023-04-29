package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    //Declarations
    Dao<WatchlistMovieEntity, Long> movieEntityDao;

    //+++++++++++++++++++++++++++++++++++++++++++++++++

    //Required Methods

    public void removeFromWatchlist(Movie movie) throws SQLException {
        movieEntityDao.delete(chosenMovie(movie));
    }

    //This is the Method List<WatchlistEntity>getAll() but we changed the name for better Comprehensiveness
    public List<WatchlistMovieEntity> getAllMoviesFromWatchlist() throws SQLException {
        return movieEntityDao.queryForAll();

    } //fehlt bei dieser Methode noch was? bitte überprüfen

    public void addToWatchlist(Movie movie) throws SQLException {
        movieEntityDao.create(chosenMovie(movie));
    }
    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        movieEntityDao.create(chosenMovie(movie));
    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Additional Methods and Constructors

    private WatchlistMovieEntity chosenMovie(Movie movie){
        return new WatchlistMovieEntity();
    }
    private WatchlistMovieEntity chosenMovie(WatchlistMovieEntity movie){
        return new WatchlistMovieEntity();
    }
    public WatchlistRepository(){
        this.movieEntityDao=Database.getDatabase().getMovieEntityDao();
    }
}
