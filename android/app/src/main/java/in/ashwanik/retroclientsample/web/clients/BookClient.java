package in.ashwanik.retroclientsample.web.clients;

import java.util.List;

import in.ashwanik.retroclient.interfaces.RequestCall;
import in.ashwanik.retroclientsample.models.Book;
import retrofit2.http.GET;

public interface BookClient {
    @GET("/books")
    RequestCall<List<Book>> books();
}