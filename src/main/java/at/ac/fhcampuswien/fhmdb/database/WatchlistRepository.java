package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    //Declarations
    Dao<WatchlistMovieEntity, Long> movieEntityDao;

    //+++++++++++++++++++++++++++++++++++++++++++++++++

    //Required Methods

    public void removeFromWatchlist(Movie movie) throws SQLException {
        //movieEntityDao.delete(chosenMovie(movie));

        try {
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = movieEntityDao.deleteBuilder();
            deleteBuilder.where().eq("apiID", movie.ApiID());
            deleteBuilder.delete();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        //This is the Method List<WatchlistEntity>getAll() but we changed the name for better Comprehensiveness
    public List<WatchlistMovieEntity> getAllMoviesFromWatchlist() throws SQLException {

        return movieEntityDao.queryForAll();

    } //fehlt bei dieser Methode noch was? bitte überprüfen

    public void addToWatchlist(Movie movie) throws SQLException {
        String title = movie.getMovieTitle();
        movieEntityDao.create(chosenMovie(movie));

        System.out.println("Added " + title + "to Watchlist");
    }
    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        movieEntityDao.create(chosenMovie(movie));
    }

    private WatchlistMovieEntity movieToEntity(Movie movie)
    {
        return new WatchlistMovieEntity(movie.ApiID(), movie.getMovieTitle(), movie.getDescription(), movie.getGenres(), movie.getReleaseYear(), movie.getRating(), movie.getLengthInMinutes(), movie.getImgUrl());
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Additional Methods and Constructors

    private WatchlistMovieEntity chosenMovie(Movie movie){
        return movieToEntity(movie);

    }
    private WatchlistMovieEntity chosenMovie(WatchlistMovieEntity movie){
        return new WatchlistMovieEntity();
    }
    public WatchlistRepository() throws DatabaseException{
        this.movieEntityDao = Database.getDatabase().getMovieEntityDao();
    }


}
