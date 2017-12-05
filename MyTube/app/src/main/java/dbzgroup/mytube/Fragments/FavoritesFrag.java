package dbzgroup.mytube.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.api.services.youtube.YouTube;

import java.util.List;

import dbzgroup.mytube.Model.MyVideo;
import dbzgroup.mytube.Model.VideoAdapter;
import dbzgroup.mytube.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFrag extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private Handler handler;
    private List<MyVideo> myVideoList;
    private VideoAdapter adapter;

    /**
     * Here I want to display all videos in a recyclerView that are in the user's playlist called "SJSU-CMPE-137" from their own channel.
     *
     * This playlist was supposed to be created in VideoAdapter.java and it was called from SearchFrag.java
     *  (It was to be created when the <3 ImageButton was pressed)
     */


    public FavoritesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorites, container, false);



        return v;
    }

    //When playlist is found/created put all videos in here...
    private void updateVideosFound(){
        adapter = new VideoAdapter(v.getContext(), myVideoList);
        recyclerView.setAdapter(adapter);
    }
}
