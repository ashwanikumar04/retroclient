package in.ashwanik.retroclientsample.web.clients;

import in.ashwanik.retroclientsample.models.Author;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthorClient {
    @GET("/authors/{authorId}")
    Call<Author> get(@Path("authorId") Integer authorId);

}