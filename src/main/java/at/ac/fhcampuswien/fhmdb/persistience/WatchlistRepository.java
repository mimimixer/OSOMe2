package at.ac.fhcampuswien.fhmdb.persistience;

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

    public void removeFromWatchlist(Movie movie) throws DatabaseException{
        //movieEntityDao.delete(chosenMovie(movie));
        try {
            DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = movieEntityDao.deleteBuilder();
            deleteBuilder.where().eq("apiID", movie.ApiID());
            deleteBuilder.delete();

        } catch (SQLException e) {
            throw new DatabaseException("could not remove movie "+movie.getMovieTitle()+"from watchlist");
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
            throw new DatabaseException();
        }

        System.out.println("Added " + title + "to Watchlist");
    }
    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try{
            movieEntityDao.create(chosenMovie(movie));
        }catch(SQLException e){
            throw new DatabaseException();
        }
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
