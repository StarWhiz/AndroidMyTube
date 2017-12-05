package dbzgroup.mytube.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.auth.api.Auth;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.squareup.picasso.Picasso;



import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Calendar;

import java.util.List;

import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.videos.Video;

import dbzgroup.mytube.PlayerActivity;
import dbzgroup.mytube.R;

/**
 * Created by Froz on 12/3/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mCtx;
    private List<MyVideo> myVideoList;
    private static YouTube youtube; //global instance of youtube object
    private View view;
    public static final String KEY = "AIzaSyAQcZqxCudMtxgUgpeSVR-xU292LAWWVXE";
    private VideoViewHolder vholder;

    public VideoAdapter(Context mCtx, List<MyVideo> myVideoList) {
        this.mCtx = mCtx;
        this.myVideoList = myVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        view = inflater.inflate(R.layout.video_card_view, null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        MyVideo myVideo = myVideoList.get(position);
        vholder = holder; //don't know if i will be needing this
        holder.videoTitle.setText(myVideo.getTitle());
        holder.videoPubDate.setText("Published Date " + myVideo.getPubDate());
        holder.videoViewCount.setText("Views " + myVideo.getNumberOfViews());
        Picasso.with(mCtx).load(myVideo.getThumbnailURL()).into(holder.videoThumbnail);
        holder.setVideoIDForPlayer(myVideo.getVideoID().toString());
    }

    @Override
    public int getItemCount() {
        return myVideoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageButton videoThumbnail;
        TextView videoTitle;
        TextView videoPubDate;
        TextView videoViewCount;
        ImageButton favButton;
        Boolean favorited = false;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoPubDate = itemView.findViewById(R.id.videoPubDate);
            videoViewCount = itemView.findViewById(R.id.videoViewCount);
            favButton = itemView.findViewById(R.id.starButton);

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!favorited) {
                        favButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                        favorited = true;
                        performPlaylistOP();
                        System.out.println("hello! perform op!");
                    }
                    else {
                        favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        favorited = false;
                    }

                }
            });
        }
        public void setVideoIDForPlayer(final String vID){
            //To Play Video
            videoThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mCtx, PlayerActivity.class);
                    System.out.println(vID);
                    intent.putExtra("VIDEO_ID", vID );
                    mCtx.startActivity(intent);
                }
            });
        }

    }

    public void performPlaylistOP () {

        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {
            }
        }).setApplicationName(view.getContext().getString(R.string.app_name)).build();

        Parser parser = new Parser();

        //(CHANNEL_ID, NUMBER_OF_RESULT_TO_SHOW, ORDER_TYPE ,BROSWER_API_KEY)
        //https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
        //The maximum number of result to show is 50
        //ORDER_TYPE --> by date: "Parser.ORDER_DATE" or by number of views: "ORDER_VIEW_COUNT"
        String url = parser.generateRequest("PLEx_MIGn6OF6bCXYYoLG-XtKKVQbquAlh", 10, Parser.ORDER_DATE, KEY);
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                Picasso.with(mCtx).load(list.get(1).
                //vholder.videoThumbnail
                //what to do when the parsing is done
                //the ArrayList contains all video data. For example you can use it for your adapter
            }

            @Override
            public void onError() {
                //what to do in case of error
            }
        });

        /*
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,status");
            parameters.put("onBehalfOfContentOwner", "");


            Playlist playlist = new Playlist();
            PlaylistSnippet snippet = new PlaylistSnippet();
            PlaylistStatus status = new PlaylistStatus();

            playlist.setSnippet(snippet);
            playlist.setStatus(status);

            YouTube.Playlists.Insert playlistsInsertRequest = youtube.playlists().insert(parameters.get("part").toString(), playlist);

            if (parameters.containsKey("onBehalfOfContentOwner") && parameters.get("onBehalfOfContentOwner") != "") {
                playlistsInsertRequest.setOnBehalfOfContentOwner(parameters.get("onBehalfOfContentOwner").toString());
            }

            Playlist response = playlistsInsertRequest.execute();
            System.out.println(response);

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }*/
    }
}
