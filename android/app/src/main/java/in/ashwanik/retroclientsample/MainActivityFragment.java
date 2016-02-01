package in.ashwanik.retroclientsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.ashwanik.retroclient.entities.ErrorData;
import in.ashwanik.retroclient.interfaces.RequestHandler;
import in.ashwanik.retroclient.service.RetroClientServiceGenerator;
import in.ashwanik.retroclientsample.models.Author;
import in.ashwanik.retroclientsample.models.Book;
import in.ashwanik.retroclientsample.models.ErrorBody;
import in.ashwanik.retroclientsample.web.clients.AuthorClient;
import in.ashwanik.retroclientsample.web.clients.BookClient;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    View view;

    @Bind(R.id.getById)
    Button getById;
    @Bind(R.id.getAll)
    Button getAll;
    @Bind(R.id.create)
    Button create;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.delete)
    Button delete;
    @Bind(R.id.unauthorized)
    Button unauthorized;
    @Bind(R.id.authorized)
    Button authorized;
    @Bind(R.id.log)
    TextView log;

    private void show(String message) {
        log.setText(log.getText() + "\n" + message);
    }

    private void showSnackBar(ErrorData errorData, final View button) {
        String message = errorData.getMessage();
        try {
            ErrorBody error = errorData.getError(ErrorBody.class);
            if (error != null) {
                message = error.message;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Snackbar.make(button, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick(button);
                    }
                }).show();
    }

    @OnClick({R.id.getAll, R.id.getById, R.id.create, R.id.update, R.id.delete, R.id.unauthorized, R.id.authorized})
    public void onButtonClick(final View button) {
        RetroClientServiceGenerator serviceGenerator = new RetroClientServiceGenerator(MainActivityFragment.this.getActivity(), false);
        BookClient client = serviceGenerator.getService(BookClient.class);
        switch (button.getId()) {
            case R.id.getById:
                serviceGenerator.execute(client.get(1), new RequestHandler<Book>() {
                    @Override
                    public void onSuccess(Book response) {
                        show("Book found:" + response.name);
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                        Log.d("RetrofitSample", errorData.toString());
                    }
                });
                break;
            case R.id.getAll:
                serviceGenerator.execute(client.get(), new RequestHandler<List<Book>>() {
                    @Override
                    public void onSuccess(List<Book> response) {
                        show("Book found: " + response.size());
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                        Log.d("RetrofitSample", errorData.toString());
                    }
                });
                break;
            case R.id.create:
                serviceGenerator.execute(client.create(new Book("Book_" + System.currentTimeMillis())), new RequestHandler<Book>() {
                    @Override
                    public void onSuccess(Book response) {
                        show("Book found:" + response.name);
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                        Log.d("RetrofitSample", errorData.toString());
                    }
                });
                break;
            case R.id.update:
                serviceGenerator.execute(client.update(new Book("Book_" + System.currentTimeMillis()), 1), new RequestHandler<Book>() {
                    @Override
                    public void onSuccess(Book response) {
                        show("Book found:" + response.name);
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                        Log.d("RetrofitSample", errorData.toString());
                    }
                });
                break;
            case R.id.delete:
                serviceGenerator.execute(client.delete(1), new RequestHandler<Void>() {

                    @Override
                    public void onSuccess(Void response) {
                        show("Successfully deleted");
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                    }
                });
                break;
            case R.id.unauthorized:
                AuthorClient unauthorizedAuthor = serviceGenerator.getService(AuthorClient.class, "unauthorizedAuthor");
                serviceGenerator.execute(unauthorizedAuthor.get(1), new RequestHandler<Author>() {

                    @Override
                    public void onSuccess(Author response) {
                        show("Author: " + response.name);
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                    }
                });
                break;
            case R.id.authorized:
                Map<String, String> headers = new HashMap<>();
                headers.put("x-access-token", "test");

                RetroClientServiceGenerator authorizedServiceGenerator = new RetroClientServiceGenerator(MainActivityFragment.this.getActivity(), false, headers);
                AuthorClient authorizedAuthor = authorizedServiceGenerator.getService(AuthorClient.class, "authorizedAuthor");
                serviceGenerator.execute(authorizedAuthor.get(1), new RequestHandler<Author>() {

                    @Override
                    public void onSuccess(Author response) {
                        show("Author: " + response.name);
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        showSnackBar(errorData, button);
                    }
                });
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        log.setText("");
        log.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
