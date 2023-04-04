package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import okhttp.*;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

@Test
public void whenGetRequestWithQueryParameter_thenCorrect()
        throws IOException {

        HttpUrl.Builder urlBuilder
        = HttpUrl.parse(BASE_URL + "/ex/bars").newBuilder();
        urlBuilder.addQueryParameter("id", "1");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
         .url(url)
         .build();
             Call call = client.newCall(request);
             Response response = call.execute();

        assertThat(response.code(), equalTo(200));
        }

@Test
public void whenGetRequest_thenCorrect() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/date")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(200));
        }

@Test
public void whenSetDefaultHeader_thenCorrect()
        throws IOException {
          OkHttpClient client = new OkHttpClient.Builder()
              .addInterceptor(
          new DefaultContentTypeInterceptor("application/json"))
              .build();

        Request request = new Request.Builder()
                .url(SAMPLE_URL)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
        }

@Test
public void whenSetHeader_thenCorrect() throws IOException {
        Request request = new Request.Builder()
                .url(SAMPLE_URL)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
        }

@Test
public void whenAsynchronousGetRequest_thenCorrect() {
        Request request = new Request.Builder()
           .url(BASE_URL + "/date")
           .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
            throws IOException {
                System.out.println("IO Exception because asynchronous call did not work?");
        }

            public void onFailure(Call call, IOException e) {
                fail();
            }
        });
    }

}