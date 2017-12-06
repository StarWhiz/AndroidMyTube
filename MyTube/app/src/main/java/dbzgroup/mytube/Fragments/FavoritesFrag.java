package dbzgroup.mytube.Fragments;


import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
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

    FirebaseDatabase database;
    DatabaseReference videoFavoritesList;

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

        database = FirebaseDatabase.getInstance();
        videoFavoritesList = database.getReference("Favorites");

        myVideoList = new ArrayList<MyVideo>();
        handler = new Handler();

        /**
         * For  DB loading
         */
        loadFavsFromDatabase(videoFavoritesList);
        updateVideosFromDB();

        return v;
    }

    /**
     * Instead of searching we just load favorites playlist directly instead then launch this
     * function to put the data loaded into the adapter...
     */
    private void updateVideosFromDB(){
        adapter = new VideoAdapter(v.getContext(), myVideoList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Load favorites from database into myVideoList
     * @param db
     */
    public void loadFavsFromDatabase (DatabaseReference db) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while(dataSnapshots.hasNext()) {
                    DataSnapshot videoItem = dataSnapshots.next();
                    MyVideo video = videoItem.getValue(MyVideo.class); // This is the request object.
                    //if(video.getUserId().equals(currentUser)) {
                        //System.out.println("condition fulfilled user is: " + order.getUserId());
                        myVideoList.add(video);
                        //System.out.println("This is the order " + order);
                    //}
                }
                // loadFavList(); Maybe definied in video adapter?
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
