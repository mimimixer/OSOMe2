package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.enums.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "watchlist")

public class WatchlistMovieEntity{
    //Declarations
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String apiID; //there should be a GET that asks for the MovieId
    @DatabaseField
    private  String title;
    @DatabaseField
    private  String description;
    @DatabaseField
    private  String genres; //muss in String umgewandelt werden!!!
    @DatabaseField
    private  int releaseYear; //GET asks for releaseYear
    @DatabaseField
    private  String imgUrl;
    @DatabaseField
    private  int lengthInMinutes;
    @DatabaseField
    private  double rating; //GET asks for ratingFrom

    //+++++++++++++++++++++++++++++++++++++++++++++++++

    //Required Methods
    private String genresToString(List<Genre> genres){
        String genresAsString = genres.stream().map(Object::toString).collect(Collectors.joining(","));
        //23.04.28 https://stackoverflow.com/questions/24882927/using-streams-to-convert-a-list-of-objects-into-a-string-obtained-from-the-tostr
         return genresAsString.toString();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


public WatchlistMovieEntity(){
 //ORM lite needs a no-arg constructor
}

//here the constructur with only one argument
    public WatchlistMovieEntity(String apiID) {    //well, worth a shot? it caused problems in Database:
        this.apiID = apiID;                        //either a movie without parameters, or one with ALL
        this.title = "this.getTitle()";              //parameters, but how do I convert the List<Genres>
        this.description = "this.getDescription()";  //into String genres? would need a parser beforehand!
      //  List<Movie> genresList=this.getGenres(); //cannot parse in parameter declaration
        this.genres = "genresToString()";
        this.releaseYear = 0;
        this.rating = 0;
        //  this.writers = writers;
        //  this.directors = directors;
        //   this.mainCast = mainCast;
        this.lengthInMinutes = 0;
        this.imgUrl = "this.getImgUrl()";
    }

 //here the constructor with all arguments
    public WatchlistMovieEntity(String apiID, String title, String description, List<Genre> genres, int releaseYear, double rating,
                                int lengthInMinutes, String imgUrl) {
        this.apiID = apiID;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.releaseYear = releaseYear;
        this.rating = rating;
        //  this.writers = writers;
        //  this.directors = directors;
        //   this.mainCast = mainCast;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
    }

    public String getApiID(){
        return apiID;
    }
    public String getMovieTitle() {
        return title;
    }
    public String getGenres() {
        return genres;
    }
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

    /* well we cannot do it because we would trash our beautiful genresToString Method
    @Override
    public String toString(){
        String s="";
        return s;
    }*/

    //lets do another moviegetter...
    public String watchlistMovieToString(){
        StringBuilder concaty=new StringBuilder();
        concaty.append(this.getApiID()+ " ,");
        concaty.append(this.getMovieTitle()+ " ,");
        concaty.append(this.getGenres()+ " ,");
        concaty.append(this.getReleaseYear()+ " ,");
        concaty.append(this.getDescription()+ " ,");
        concaty.append(this.getImgUrl()+ " ,");
        concaty.append(this.getLengthInMinutes()+ " ,");
        concaty.append(this.getRating());
        return concaty.toString();
    }
}