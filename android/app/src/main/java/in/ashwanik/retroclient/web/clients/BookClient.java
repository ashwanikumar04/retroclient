package in.ashwanik.retroclient.web.clients;

import java.util.List;

import in.ashwanik.retroclient.interfaces.RequestCall;
import in.ashwanik.retroclient.models.Book;
import retrofit2.http.GET;

public interface BookClient {
    @GET("/books")
    RequestCall<List<Book>> books();
}