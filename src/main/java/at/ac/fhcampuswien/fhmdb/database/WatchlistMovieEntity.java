package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.enums.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "watchlist")

public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    String movieId; //there should be a GET that asks for the MovieId
    @DatabaseField
    private  String title;
    /*@DatabaseField
    private  List<Genre> genres; //muss in String umgewandelt werden!!!
   */
    @DatabaseField
    private  int releaseYear; //GET asks for releaseYear
    @DatabaseField
    private  String description;
    @DatabaseField
    private  String imgUrl;
    @DatabaseField
    private  int lengthInMinutes;
    @DatabaseField
    private  double rating; //GET asks for ratingFrom

public WatchlistMovieEntity(){
 //ORM lite needs a no-arg constructor
}
 //here the constructor with arguments
    public WatchlistMovieEntity(String movieId, String title, String description,  int releaseYear, double rating,
                                int lengthInMinutes, String imgUrl) { //List<Genre> genres,
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        // this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        //  this.writers = writers;
        //  this.directors = directors;
        //   this.mainCast = mainCast;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
    }

    public String getMovieId(){
        return movieId;
    }
    public String getTitle() {
        return title;
    }
  /*  public List<Genre> getGenres() {
        return genres;
    }*/
    public int getReleaseYear(){
        return releaseYear;
    }
    public String getDescription() {
        return description;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public int getLengthInMinutes() {
        return lengthInMinutes;
    }
    public double getRating() {
        return rating;
    }
}
