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
URL url= HttpUrl.parse("http://prog2.fh-campuswien.ac.at/movies").url(); //https://stackoverflow.com/questions/39498767/build-url-in-java 4.4.23

  //  private static final String DELIMITER = "&";

    //https://square.github.io/okhttp/#requirements 5.4.23
   private static OkHttpClient client = new OkHttpClient();
/*
    public String buildUrl(String queryText, String chosenGenre, int chosenReleaseYear, int chosenRatingFrom) throws IOException, NullPointerException {
        HttpUrl urlWithQueryParameters = new HttpUrl.Builder()
                .scheme("https")
                .host("prog2.fh-campuswien.ac.at/movies")
                //     .addPathSegment("movies")
                //      .addPathSegment("v1")
                .addQueryParameter("query", queryText)
                .addQueryParameter("genre", chosenGenre)
                .addQueryParameter("releaseYear", Integer.toString(chosenReleaseYear))
                .addQueryParameter("ratingFrom", Integer.toString(chosenRatingFrom))
                .build();

        System.out.println(buildUrl().toString());

        Request requesthttp = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl) // <- Finally put httpUrl in here
                .build();

        Response response = client.newCall(requesthttp).execute();
        return response.body().string();
    }
}

*/
    public static List<Movie> getDataBaseFromInternet(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
            //        .addHeader("accept", "application/json") //7.4.23 https://stackoverflow.com/questions/30142626/how-to-add-query-parameters-to-a-http-get-request-by-okhttp
                    .addHeader("User-Agent", "http.agent") //Tutorium 24.3.23
                    .build();
            try (Response response = client.newCall(request).execute()) {
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


  /*  Movie outputMovie = new Gson().fromJson(jsonInput, Movie.class);
    outputMovie.toString();
    allMovies = run(BASE);


  String jsonInput = "{\"imdbId\":\"tt0472043\",\"actors\":" +
          "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\"," +
          "\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";
*/
}
