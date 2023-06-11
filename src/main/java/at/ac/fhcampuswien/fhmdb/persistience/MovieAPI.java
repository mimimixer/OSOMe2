package at.ac.fhcampuswien.fhmdb.persistience;

import at.ac.fhcampuswien.fhmdb.customExceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.Gson;

import com.google.gson.JsonParseException;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {



    private static final String BASE = "http://prog2.fh-campuswien.ac.at";
  //  private static final String scheme="http";
    private static final String path = "/movies";

    private static final String DELI = "&";
    private static OkHttpClient client = new OkHttpClient();

    public static String getPath() {
        return path;
    }

    public static String getBASE() {
        return BASE;
    }
    /*   private static String makeUrl(String query, String genre, String releaseYear, String ratingFrom) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(scheme)
                .host(BASE)
                .addPathSegment(path)
               // if(query!=null&&!query.isEmpty()){
                .addQueryParameter("query", query)
                .addQueryParameter("genre", genre)
                .addQueryParameter("releaseYear", releaseYear)
                .addQueryParameter("ratingFrom", ratingFrom)
                .build();
        String url=httpUrl.toString();
        //String url;
          URLBuilder builder=new URLBuilder();
    String url=builder
            .setHost(BASE)
            .setPath(path)
            .setQuery(query)
            .setGenre(genre)
            .setReleaseYear(releaseYear)
            .setRatingFrom(ratingFrom)
            .build();
    System.out.println(url);
    return url;
    }



    private static String makeUrl(String movieId){

        URLBuilder builder=new URLBuilder();
        String url=builder
                .setHost(BASE)
                .setPath(path)
                .setMovieID(movieId)
                .build();
        System.out.println(url);
        return url;

        /*
        StringBuilder url = new StringBuilder(BASE);
        if(movieId != null){
            url.append("/").append(movieId);
        }
        return url.toString();


    }*/

    public static String requestUrl(String madeUrl) throws IOException {
        System.out.println("lets make the request @ " +madeUrl);
        Request requesthttp = new Request.Builder()
                //   .addHeader("accept", "application/json")
                .addHeader("User-Agent", "http.agent") //Tutorium 24.3.23
                .url(madeUrl) // <- Finally put httpUrl in here
                .build();
        Response response = client.newCall(requesthttp).execute();
            return response.body().string();
            //System.out.println("GET MovieDataBase did not work out");
            //ex.printStackTrace();
        }


    public static List<Movie> parseMovieListToJson (String requestedDataByUrl) throws JsonParseException{
        Movie[] outputMovie = new Gson().fromJson(requestedDataByUrl, Movie[].class);//https://www.baeldung.com/jackson-vs-gson 6.4.23
        List<Movie> listAllMovies= Arrays.asList(outputMovie); //https://www.geeksforgeeks.org/conversion-of-array-to-arraylist-in-java/ 6.4.23
        return listAllMovies;
    }

  /*  public static Movie getThatMovieSpecificDown(String movieId){
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
    *
   */

    public static List<Movie> getAllMoviesDown(String BASE) throws MovieApiException {
        try{
            return parseMovieListToJson(requestUrl(BASE));
        }catch(Exception ex){
            throw new MovieApiException("Error while executing request: " + ex.getMessage());
        }
    }

  /*  public static List<Movie> getThatMovieListDown (String queryText, String chosenGenre,
                                                    String chosenReleaseYear, String chosenRatingFrom) throws MovieApiException {
        String newUrl = makeUrl(queryText, chosenGenre, chosenReleaseYear, chosenRatingFrom);
        String getData = null;
        try {
            getData = requestUrl(newUrl);
            List<Movie> parsedMovielist=parseMovieListToJson(getData);
            return parsedMovielist;
        }catch (IOException|JsonParseException e) {
            throw new MovieApiException("Error while executing request: " + e.getMessage());
        }
    }*/
}
