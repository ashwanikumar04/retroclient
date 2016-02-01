package in.ashwanik.retroclientsample.web.clients;

import java.util.List;

import in.ashwanik.retroclient.clients.BaseRetroClient;
import in.ashwanik.retroclient.interfaces.RequestCall;
import in.ashwanik.retroclientsample.models.Book;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookClient extends BaseRetroClient {
    @GET("/books/{bookId}")
    RequestCall<Book> get(@Path("bookId") Integer bookId);

    @GET("/books")
    RequestCall<List<Book>> get();

    @POST("/books")
    RequestCall<Book> create(@Body Book book);

    @PUT("/books/{bookId}")
    RequestCall<Book> update(@Body Book book, @Path("bookId") Integer bookId);

    @DELETE("/books/{bookId}")
    RequestCall<Book> delete(@Path("bookId") Integer bookId);
}