package in.ashwanik.retroclientsample.web.clients;

import java.util.List;

import in.ashwanik.retroclientsample.models.Book;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookClient {
    @GET("/books/{bookId}")
    Call<Book> get(@Path("bookId") Integer bookId);

    @GET("/books")
    Call<List<Book>> get();

    @POST("/books")
    Call<Book> create(@Body Book book);

    @PUT("/books/{bookId}")
    Call<Book> update(@Body Book book, @Path("bookId") Integer bookId);

    @DELETE("/books/{bookId}")
    Call<Void> delete(@Path("bookId") Integer bookId);
}