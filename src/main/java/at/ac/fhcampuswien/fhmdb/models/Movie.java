package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.enums.Genre;

import java.util.*;

public class Movie{
    private  String id;
    private  String title;
    private  List<Genre> genres;
    private  int releaseYear; //GET asks for releaseYear
    private  String description;
    private  String imgUrl;
    private  int lengthInMinutes;
    private  List<String> directors=new ArrayList<>();
    private  List<String> writers=new ArrayList<>();
    private  List<String> mainCast=new ArrayList<>();
    private  double rating; //GET asks for ratingFrom

    public Movie(String title, String description, List<Genre> genres) {
        this.id = "";
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = 0;
        this.imgUrl = "";
        this.lengthInMinutes = 0;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = 0;
    }

    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, double rating,
                 int lengthInMinutes, String imgUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        //this.writers = getWriters();
        //this.directors = getDirectors();
        //this.mainCast = getMainCast();
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
    }
    public Movie(String id, String title, String description, List<Genre> genres, int releaseYear, double rating,
                 List<String> writers, List<String> directors, List<String> mainCast,
                 int lengthInMinutes, String imgUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.writers = writers;
        this.directors = directors;
        this.mainCast = mainCast;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getApiID(){
        return id;
    }
    public String getMovieTitle() {
        return title;
    }
    public List<Genre> getGenres() {
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
    public List<String> getDirectors() {
        return directors;
    }
    public List<String> getWriters() {
        return writers;
    }
    public List<String> getMainCast() {
        return mainCast;
    }
    public double getRating() {
        return rating;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Life Is Beautiful",
                "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));
        movies.add(new Movie(
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)));
        movies.add(new Movie(
                "Puss in Boots",
                "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)));
        movies.add(new Movie(
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));
        movies.add(new Movie(
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY)));

        return movies;
    }

}




