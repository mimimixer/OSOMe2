package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MovieAPI {
    public List<Movie> allMovies;
    private static final String BASE = "http://prog2.fh-campuswien.ac.at/movies";
    private static final String DELI = "&";

//URL url= HttpUrl.parse("http://prog2.fh-campuswien.ac.at/movies").url(); //https://stackoverflow.com/questions/39498767/build-url-in-java 4.4.23

  //  private static final String DELIMITER = "&";

    //https://square.github.io/okhttp/#requirements 5.4.23
   private static OkHttpClient client = new OkHttpClient();
   /* public static String makeUrl(String queryText, String chosenGenre, String chosenReleaseYear, String chosenRatingFrom) {
        System.out.println("hello");
        String urlWithQueryParameters = BASE;

        if((queryText != null && !queryText.isEmpty()) || chosenGenre != null || chosenReleaseYear != null|| chosenRatingFrom != null){
            urlWithQueryParameters=urlWithQueryParameters+"?";
            if(queryText != null && !queryText.isEmpty()){
                urlWithQueryParameters=urlWithQueryParameters+"query="+queryText+"&";
            }
            if(chosenGenre != null){
                urlWithQueryParameters=urlWithQueryParameters+"genre="+chosenGenre+"&";
            }
            if (chosenReleaseYear != null) {
                urlWithQueryParameters= urlWithQueryParameters+"releaseYear="+chosenReleaseYear+"&";
            }
            if (chosenRatingFrom != null) {
                urlWithQueryParameters=urlWithQueryParameters+"ratingFrom="+chosenRatingFrom+"&";
            }
        }
        System.out.println("this the url with query"+urlWithQueryParameters);
        return urlWithQueryParameters;
        }
*/

    public static String makeUrl(String queryText, String chosenGenre, String chosenReleaseYear, String chosenRatingFrom) {
        System.out.println("hello");
        StringBuilder urlWithQueryParameters = new StringBuilder(BASE);

        if((queryText != null && !queryText.isEmpty()) || chosenGenre != null || chosenReleaseYear != null|| chosenRatingFrom != null){
            urlWithQueryParameters.append("?");
            if(queryText != null && !queryText.isEmpty()){
                urlWithQueryParameters.append("query=").append(queryText).append(DELI);
            }
            if(chosenGenre != null && !chosenGenre.equals("No filter")){
                urlWithQueryParameters.append("genre=").append(chosenGenre).append(DELI);
            }
            if (chosenReleaseYear != null && !chosenReleaseYear.equals("No filter")) {
                urlWithQueryParameters.append("releaseYear=").append(chosenReleaseYear).append(DELI);
            }
            if (chosenRatingFrom != null && !chosenRatingFrom.equals("No filter")) {
                urlWithQueryParameters.append("ratingFrom=").append(chosenRatingFrom).append(DELI);
            }
        }
        System.out.println("this the url with query"+urlWithQueryParameters.toString());
     /*   return urlWithQueryParameters.toString();
            HttpUrl urlWithQueryParameters = new HttpUrl.Builder()
                .scheme("https")
                .host(BASE)
                //     .addPathSegment("movies")
                //      .addPathSegment("v1")
                .addQueryParameter("query", queryText)
                .addQueryParameter("genre", chosenGenre)
                .addQueryParameter("releaseYear", Integer.toString(chosenReleaseYear))
                .addQueryParameter("ratingFrom", Double.toString(chosenRatingFrom))
                .build();
        System.out.println("this the url with query"+urlWithQueryParameters.toString());
      */
        return urlWithQueryParameters.toString();
    }

    public static String requestUrl(String madeUrl){
    System.out.println("lets make the request @ " +madeUrl);
    Request requesthttp = new Request.Builder()
         //   .addHeader("accept", "application/json")
            .addHeader("User-Agent", "http.agent") //Tutorium 24.3.23
            .url(madeUrl) // <- Finally put httpUrl in here
            .build();
    try(Response response = client.newCall(requesthttp).execute()){
        return response.body().string();
    }catch (Exception ex){
        System.out.println("GET MovieDataBase did not work out");
        ex.printStackTrace();
    }
            return null;
}
    public static List<Movie> parseMovieListToJson (String requestedDataByUrl){
    Movie [] outputMovie = new Gson().fromJson(requestedDataByUrl, Movie[].class); //https://www.baeldung.com/jackson-vs-gson 6.4.23
    List<Movie> listAllMovies= Arrays.asList(outputMovie); //https://www.geeksforgeeks.org/conversion-of-array-to-arraylist-in-java/ 6.4.23
                return listAllMovies;
        }

public static List<Movie> getAllMoviesDown(String BASE){
        return parseMovieListToJson(requestUrl(BASE));
}

public static List<Movie> getThatMovieListDown (String queryText, String chosenGenre,
                                                String chosenReleaseYear, String chosenRatingFrom){
        String newUrl = makeUrl( queryText, chosenGenre, chosenReleaseYear, chosenRatingFrom);
        String getData = requestUrl(newUrl);
        List<Movie> parsedMovielist=parseMovieListToJson(getData);
        return parsedMovielist;
        }
/*
    public static List<Movie> getDataBaseFromInternet(String url)  throws IOException, NullPointerException{
            Request requests = new Request.Builder()
                    .url(url)
            //        .addHeader("accept", "application/json") //7.4.23 https://stackoverflow.com/questions/30142626/how-to-add-query-parameters-to-a-http-get-request-by-okhttp
                    .addHeader("User-Agent", "http.agent") //Tutorium 24.3.23
                    .build();
            try (Response response = client.newCall(requests).execute()) {
                String hugeAmountOfChaoticData=response.body().string();
             //   System.out.println(hugeAmountOfChaoticData);
                Movie [] outputMovie = new Gson().fromJson(hugeAmountOfChaoticData, Movie[].class); //https://www.baeldung.com/jackson-vs-gson 6.4.23
                List<Movie> listAllMovies= Arrays.asList(outputMovie); //https://www.geeksforgeeks.org/conversion-of-array-to-arraylist-in-java/ 6.4.23
                return listAllMovies;
            }catch (Exception ex){
                System.out.println("GET MovieDataBase did not work out");
                ex.printStackTrace();
            }
            return null;
    }
*/

  /*  Movie outputMovie = new Gson().fromJson(jsonInput, Movie.class);
    outputMovie.toString();
    allMovies = run(BASE);


  String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" +
          "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\"," +
          "\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
*/
}
