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

import com.google.android.gms.auth.api.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ImageButton favButton;
    private Boolean favorited = false;

    private YouTube youtube;

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


        //For favorites button
        favButton = v.findViewById(R.id.starButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favorited) {
                    favButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorited = true;

                    List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
                    // This object is used to make YouTube Data API requests.
                    youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                            .setApplicationName("youtube-cmdline-playlistupdates-sample")
                            .build();

                    // Create a new, private playlist in the authorized user's channel.
                    String playlistId = insertPlaylist();

                    // If a valid playlist was created, add a video to that playlist.
                    insertPlaylistItem(playlistId, VIDEO_ID);
                }
                else {
                    favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    favorited = false;
                }

            }
        });

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


    /**
     * Create a playlist and add it to the authorized account.
     */
    private static String insertPlaylist() throws IOException {

        // This code constructs the playlist resource that is being inserted.
        // It defines the playlist's title, description, and privacy status.
        PlaylistSnippet playlistSnippet = new PlaylistSnippet();
        playlistSnippet.setTitle("Test Playlist " + Calendar.getInstance().getTime());
        playlistSnippet.setDescription("A private playlist created with the YouTube API v3");
        PlaylistStatus playlistStatus = new PlaylistStatus();
        playlistStatus.setPrivacyStatus("private");

        Playlist youTubePlaylist = new Playlist();
        youTubePlaylist.setSnippet(playlistSnippet);
        youTubePlaylist.setStatus(playlistStatus);

        // Call the API to insert the new playlist. In the API call, the first
        // argument identifies the resource parts that the API response should
        // contain, and the second argument is the playlist being inserted.
        YouTube.Playlists.Insert playlistInsertCommand =
                youtube.playlists().insert("snippet,status", youTubePlaylist);
        Playlist playlistInserted = playlistInsertCommand.execute();

        // Print data from the API response and return the new playlist's
        // unique playlist ID.
        System.out.println("New Playlist name: " + playlistInserted.getSnippet().getTitle());
        System.out.println(" - Privacy: " + playlistInserted.getStatus().getPrivacyStatus());
        System.out.println(" - Description: " + playlistInserted.getSnippet().getDescription());
        System.out.println(" - Posted: " + playlistInserted.getSnippet().getPublishedAt());
        System.out.println(" - Channel: " + playlistInserted.getSnippet().getChannelId() + "\n");
        return playlistInserted.getId();
    }

    /**
     * Create a playlist item with the specified video ID and add it to the
     * specified playlist.
     *
     * @param playlistId assign to newly created playlistitem
     * @param videoId    YouTube video id to add to playlistitem
     */
    private static String insertPlaylistItem(String playlistId, String videoId) throws IOException {

        // Define a resourceId that identifies the video being added to the
        // playlist.
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);

        // Set fields included in the playlistItem resource's "snippet" part.
        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setTitle("First video in the test playlist");
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);

        // Create the playlistItem resource and set its snippet to the
        // object created above.
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippet);

        // Call the API to add the playlist item to the specified playlist.
        // In the API call, the first argument identifies the resource parts
        // that the API response should contain, and the second argument is
        // the playlist item being inserted.
        YouTube.PlaylistItems.Insert playlistItemsInsertCommand =
                youtube.playlistItems().insert("snippet,contentDetails", playlistItem);
        PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();

        // Print data from the API response and return the new playlist
        // item's unique playlistItem ID.

        System.out.println("New PlaylistItem name: " + returnedPlaylistItem.getSnippet().getTitle());
        System.out.println(" - Video id: " + returnedPlaylistItem.getSnippet().getResourceId().getVideoId());
        System.out.println(" - Posted: " + returnedPlaylistItem.getSnippet().getPublishedAt());
        System.out.println(" - Channel: " + returnedPlaylistItem.getSnippet().getChannelId());
        return returnedPlaylistItem.getId();
    }

}
