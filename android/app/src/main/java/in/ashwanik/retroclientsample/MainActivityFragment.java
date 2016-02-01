package in.ashwanik.retroclientsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import in.ashwanik.retroclient.entities.ErrorData;
import in.ashwanik.retroclient.interfaces.RequestHandler;
import in.ashwanik.retroclient.service.RetroClientServiceGenerator;
import in.ashwanik.retroclientsample.models.Book;
import in.ashwanik.retroclientsample.web.clients.BookClient;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);


        Button button = (Button) (view.findViewById(R.id.start));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroClientServiceGenerator serviceGenerator = new RetroClientServiceGenerator((ServiceGeneratorConfig) MainActivityFragment.this.getActivity(), false);

                BookClient client = serviceGenerator.getService(BookClient.class);

                serviceGenerator.execute(client.books(), new RequestHandler<List<Book>>() {
                    @Override
                    public void onSuccess(List<Book> response) {
                        for (Book book : response) {
                            Log.d("RetrofitSample", book.toString());
                        }
                    }

                    @Override
                    public void onError(ErrorData errorData) {
                        Log.d("RetrofitSample", errorData.toString());
                    }
                });

            }
        });

        return view;
    }
}
