package at.ac.fhcampuswien.fhmdb.models;

import java.io.*;
import okhttp3.*;
import com.google.gson.*;

public class MovieAPI {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


}
