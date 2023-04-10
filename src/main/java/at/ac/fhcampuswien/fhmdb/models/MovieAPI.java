package at.ac.fhcampuswien.fhmdb.models;

import okhttp3.*;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

public class MovieAPI {

    private static final String baseURL = "http://prog2.fh-campuswien.ac.at/movies";
    private static final String DELIMITER = "&";

    private static String buildURL(String query, String genre, String releaseYear, String ratingFrom) {
        StringBuilder url = new StringBuilder(baseURL);

        if ((query != null && !query.isEmpty()) || genre != null || releaseYear != null || ratingFrom != null) {
            url.append("?");
            if (query != null && !query.isEmpty()) {
                url.append("query=").append(query).append(DELIMITER);
            }
            if (genre != null && !genre.equals("No filter")) {
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if (releaseYear != null && !releaseYear.equals("No filter")) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
        }
        return url.toString();
    }
    private static String buildURL(String id){
        StringBuilder url = new StringBuilder(baseURL);
        if(id != null){
            url.append("/").append(id);
        }
        return url.toString();
    }

    public static Movie getSpecificMovie(String id) {
        String url = buildURL(id);
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            Movie movie = gson.fromJson(responseBody, Movie.class);

            return movie;

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }return null;
    }

    public static List<Movie> getMovies(String query, String genre, String releaseYear, String ratingFrom) {
        String url = buildURL(query, genre, releaseYear, ratingFrom);

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);

            return Arrays.asList(movies);

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return new ArrayList<>();

    }

    public static List<Movie> getAllMovies() {
        return getMovies(null, null, null, null);
    }


    //dummy function to get the String not Json
    public static String movieString(String query, String genre, String releaseYear, String ratingFrom) {
        String url = buildURL(query, genre, releaseYear, ratingFrom);

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            //Gson gson = new Gson();
            //Movie[] movies = gson.fromJson(responseBody, Movie[].class);
            //System.out.println(responseBody);
            return responseBody;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "error";
        }


    }


        /*
        try(InputStream responseStream = connection.getInputStream())
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                String title = parts[0];
                String description = parts[1];
                List<Genre> genres = Arrays.asList(parts[2].split("\\|")).stream().map(Genre::valueOf).collect(Collectors.toList());
                int year = Integer.parseInt(parts[3]);
                double ratingVal = Double.parseDouble(parts[4]);
                movies.add(new Movie(title, description, genres, year, ratingVal));
            }

        }
        return movies;

         */






        /*HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        if (query != null) {
            urlBuilder.addQueryParameter("query", query);
        }
        if (genre != null) {
            urlBuilder.addQueryParameter("genre", genre.name());
        }
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseString = response.body().string();
            Movie[] movies = gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);
        }*/
}

