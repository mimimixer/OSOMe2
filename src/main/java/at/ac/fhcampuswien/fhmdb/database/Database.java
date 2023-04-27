package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class Database {
    public static final String DB_URL = "jdbc:h2:file: ./db/moviesdb"; // "jdbc:h2:[file:][<path>]<databaseName>;
    public static final String username= "user";
    public static final String password ="pass";

    private static ConnectionSource connectionSource;

    Dao<WatchlistMovieEntity, Long> movieEntityDao;

    private static Database instance;

    public void testDB() throws SQLException {
        WatchlistMovieEntity watchlistMovie3 =new WatchlistMovieEntity("movieId123", "title", "description",  2023, 9.7,
        75, "https://tinyurl.com/4mazab5c");
        movieEntityDao.create(watchlistMovie3);
    /*    List<WatchlistMovieEntity> l= movieEntityDao.queryForAll();
        l.forEach(e -> {
            System.out.println(e.toString());
        });*/
    }

    private Database(){
        try {
            createConnectionSource();
            movieEntityDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        }catch (SQLException e){
            System.out.println("now we have that SQL Exception");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    //neue Database erstellen wenn es noch keine gibt - SINGLETON Pattern!
    public static Database getDatabase() {
        if (instance == null) {
            instance=new Database();
        }
        System.out.println(instance);
        return instance;
    }

    public static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    private static void createConnectionSource() throws SQLException {
    connectionSource = new JdbcConnectionSource( DB_URL,  username, password);
}



/*
    Dao<Account, String> accountDao =
            DaoManager.createDao(connectionSource, Account.class);
    Dao<Order, Integer> orderDao =
            DaoManager.createDao(connectionSource, Order.class);
    */
}
