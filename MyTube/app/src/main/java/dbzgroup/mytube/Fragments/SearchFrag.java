package dbzgroup.mytube.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import dbzgroup.mytube.Model.MyVideo;
import dbzgroup.mytube.Model.VideoAdapter;
import dbzgroup.mytube.NavigationActivity;
import dbzgroup.mytube.R;
import dbzgroup.mytube.YoutubeConnector;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFrag extends Fragment {
    private RecyclerView recyclerView;
    private EditText searchInput;
    private ImageButton searchButton;
    private Handler handler;
    private List<MyVideo> myVideoList;
    private VideoAdapter adapter;
    private View v;

    public SearchFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        // Inflate the layout for this fragment

        // For SearchFrag
        searchInput = v.findViewById(R.id.searchInput);
        searchButton = v.findViewById(R.id.imageButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOnYoutube(searchInput.getText().toString());
            }
        });
        myVideoList = new ArrayList<MyVideo>();
        handler = new Handler();
        return v;
    }
    /**
     * This function searches videos thru the youtube API when a keyword is passed.
     *
     * @param keywords
     */

    private void searchOnYoutube(final String keywords) {
        new Thread(){
            @Override
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(v.getContext());
                myVideoList = yc.search(keywords);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    /**
     * This function updates the view when videos are found
     */
    private void updateVideosFound(){
        adapter = new VideoAdapter(v.getContext(), myVideoList);
        recyclerView.setAdapter(adapter);
    }
}
