package at.ac.fhcampuswien.fhmdb.persistience;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    //Deklarationen
    public static final String DB_URL = "jdbc:h2:file:./db/moviesdb"; // "jdbc:h2:[file:][<path>]<databaseName>;
    public static final String username= "user";
    public static final String password ="pass";

    private static ConnectionSource connectionSource;
    private Dao<WatchlistMovieEntity, Long> movieEntityDao; //needs getter

//laut Leons Video ist eine Database instance notwendig, und nämlich GENAU EINE!
 private static Database instance;
 //nicht vergessen: database h2, user+pass angeben, jdbc:h2:file: ./db/moviesdb und
 // UNBEDINGT!!!!! embedded auswählen, nicht remote oder sonstwas!
 // und am ende deactivate clicken (roter punkt) und dann refresh!

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Required Methods
    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource( DB_URL,  username, password);
    }
    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }
    public static void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
    public Dao<WatchlistMovieEntity, Long> getMovieEntityDao() {
        return this.movieEntityDao;
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Additional Methods and Constructors

    //neue Database erstellen wenn es noch keine gibt - SINGLETON Pattern!
    public static Database getDatabase() throws DatabaseException{
        if (instance == null) {
            instance= new Database();
        }
        System.out.println(instance);
        return instance;
    }

    //Constructor
    private Database() throws DatabaseException{
        try {
            createConnectionSource();
            movieEntityDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        }catch (Exception e){ //Here we actually need Custom Databaseexception
            throw new DatabaseException("Failed to initialize database", e);
            /*
            System.out.println("now we have that SQL Exception");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
             */
        }
    }


    public void testDB() throws DatabaseException {
        try {
            WatchlistMovieEntity watchlistMovie3 = new WatchlistMovieEntity("movieId123");
            movieEntityDao.create(watchlistMovie3);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create movie entity", e);
        }
    }
    /*    List<WatchlistMovieEntity> l= movieEntityDao.queryForAll();
        l.forEach(e -> {
            System.out.println(e.toString());
        });
    }

/*
    Dao<Account, String> accountDao =
            DaoManager.createDao(connectionSource, Account.class);
    Dao<Order, Integer> orderDao =
            DaoManager.createDao(connectionSource, Order.class);
    */
}
