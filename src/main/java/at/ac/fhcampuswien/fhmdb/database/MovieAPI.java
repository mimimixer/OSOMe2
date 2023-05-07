package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.customExceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;

import com.google.gson.JsonParseException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {

    private static final String BASE = "http://prog2.fh-campuswien.ac.at/movies";
    private static final String DELI = "&";
    private static OkHttpClient client = new OkHttpClient();

    private static String makeUrl(String query, String genre, String releaseYear, String ratingFrom) {
        StringBuilder url = new StringBuilder(BASE);

        if ((query != null && !query.isEmpty()) || genre != null || releaseYear != null || ratingFrom != null) {
            url.append("?");
            if (query != null && !query.isEmpty()) {
                url.append("query=").append(query).append(DELI);
            }
            if (genre != null && !genre.equals("No filter")) {
                url.append("genre=").append(genre).append(DELI);
            }
            if (releaseYear != null && !releaseYear.equals("No filter")) {
                url.append("releaseYear=").append(releaseYear).append(DELI);
            }
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELI);
            }
        }
        return url.toString();
    }
    private static String makeUrl(String movieId){
        StringBuilder url = new StringBuilder(BASE);
        if(movieId != null){
            url.append("/").append(movieId);
        }
        return url.toString();
    }

    public static String requestUrl(String madeUrl) throws MovieApiException {
        System.out.println("lets make the request @ " +madeUrl);
        Request requesthttp = new Request.Builder()
                //   .addHeader("accept", "application/json")
                .addHeader("User-Agent", "http.agent") //Tutorium 24.3.23
                .url(madeUrl) // <- Finally put httpUrl in here
                .build();
        try(Response response = client.newCall(requesthttp).execute()){
            return response.body().string();
        }catch (IOException ex){
            throw new MovieApiException("Error while executing request: " + ex.getMessage());
            //System.out.println("GET MovieDataBase did not work out");
            //ex.printStackTrace();
        }
    }

    public static List<Movie> parseMovieListToJson (String requestedDataByUrl){
        Movie[] outputMovie = new Gson().fromJson(requestedDataByUrl, Movie[].class);//https://www.baeldung.com/jackson-vs-gson 6.4.23
        List<Movie> listAllMovies= Arrays.asList(outputMovie); //https://www.geeksforgeeks.org/conversion-of-array-to-arraylist-in-java/ 6.4.23
        return listAllMovies;
    }

    public static Movie getThatMovieSpecificDown(String movieId){
        String newUrl = makeUrl(movieId);
        String getData = null;
        try {
            getData = requestUrl(newUrl);
        } catch (NullPointerException | MovieApiException ex) {
            System.err.println("Caught a NullPointerException: " + ex.getMessage());
        }
        Movie movie = new Gson().fromJson(getData, Movie.class);
     //     List<Movie> parsedMovie=parseMovieListToJson(getData);
        return movie;
    }

    public static List<Movie> getAllMoviesDown(String BASE) throws MovieApiException {
        return parseMovieListToJson(requestUrl(BASE));
    }

public static List<Movie> getThatMovieListDown (String queryText, String chosenGenre,
                                                String chosenReleaseYear, String chosenRatingFrom) throws MovieApiException {
        String newUrl = makeUrl(queryText, chosenGenre, chosenReleaseYear, chosenRatingFrom);
        String getData = null;
        try {
             getData = requestUrl(newUrl);
        }catch (MovieApiException e) {
            throw new MovieApiException(e);
        }
        List<Movie> parsedMovielist=parseMovieListToJson(getData);
            return parsedMovielist;
        }
}
