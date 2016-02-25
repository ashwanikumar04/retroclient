# Retro-Client

Basic features:

 * Provides out of the box network check and error handling
 * Show progress view while making network calls
 * Multiple methods for initializing Retrofit services
 

## Installation

```

   compile 'in.ashwanik:retro-client:0.1.1'
   
```

## Usage
Initialize *RetroClient* using **RetroClientServiceInitializer** in an activity or Application class

```

 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressViewColor = getResources().getColor(R.color.colorPrimary, null);
        } else {
            progressViewColor = getResources().getColor(R.color.colorPrimary);
        }
        RetroClientServiceInitializer.getInstance().initialize(ApiUrls.BASE_API_URL, getApplicationContext(), progressViewColor, true);
        RetroClientServiceInitializer.getInstance().setLogCategoryName("Retro-Client-Sample");

```

### Define a client

```

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

```
### Make the call

```

RetroClientServiceGenerator serviceGenerator = new RetroClientServiceGenerator(MainActivityFragment.this.getActivity(), false);
        BookClient client = serviceGenerator.getService(BookClient.class);
        serviceGenerator.execute(client.get(1), new RequestHandler<Book>() {
            @Override
            public void onSuccess(Book response) {
                  Log.d("RetrofitSample", response.toString());
            }

            @Override
            public void onError(ErrorData errorData) {
                Log.d("RetrofitSample", errorData.toString());
            }
        });
        
```

### Options for **RetroClientServiceInitializer**
* **baseUrl**: Base Url for API
* **converterFactory**: Set converter factory. Default is JSON
* **timeOut**: Timeout (seconds) for network calls. Default is 30
* **enableRetry**: Enable request retry. Default true
* **isDebug**: Enabling debugging. Level.BODY is used for logging. Default is false
* **logCategoryName**: Log category 
* **progressViewColor**: Specify the color of progress view 
* **logger**: Logger used for logging exception etc to external services like Crashlytics.

#### Sample app is available [here](https://github.com/ashwanikumar04/retroclient/tree/master/android)
#### Sample rest API using Node.js is available [here](https://github.com/ashwanikumar04/retroclient/tree/master/api)

## History
- Released 0.1.1 version
    * Updated Retrofit version
    * Added support for changing default messages
    * Added support for changing cache directory
    * Added support for changing cache directory size
    * Code refactoring 
    
- Released 0.1.0 version


## Credits
1. Nice code for [circular progress view](https://gist.github.com/dmide/7506c7d9614eed90805d)  
2. Retrofit [Examples](https://github.com/square/retrofit/tree/master/samples)
3. Tutorials on Retrofit by [FutureStud](https://futurestud.io/blog/retrofit-getting-started-and-android-client)




For more tutorials on Android, please check [here](http://blog.ashwanik.in).