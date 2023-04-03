package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {

    private static final String baseURL = "http://prog2.fh-campuswien.ac.at/movies";
    private static final String DELIMITER = "";

    private static String buildURL(String query, String genre, String releaseYear, String ratingFrom){
        StringBuilder url = new StringBuilder(baseURL);

        if((query != null && !query.isEmpty()) || genre != null || releaseYear != null || ratingFrom != null){
            url.append("?");
            if(query != null && !query.isEmpty()){
                url.append("query=").append(query).append(DELIMITER);
            }
            if(genre != null){
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if (releaseYear != null) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
        }
        return url.toString();
    }

    public static List<Movie> getMovies(String query, String genre, String releaseYear, String ratingFrom){
        String url = buildURL(query, genre, releaseYear, ratingFrom);

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);

            return Arrays.asList(movies);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static List<Movie> getAllMovies(){
        return getMovies(null, null, null, null);
    }

}
