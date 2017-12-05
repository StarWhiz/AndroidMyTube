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

        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails");
            parameters.put("mine", "true");
            parameters.put("maxResults", "15");
            parameters.put("onBehalfOfContentOwner", "");
            parameters.put("onBehalfOfContentOwnerChannel", "");

            YouTube.Playlists.List playlistsListMineRequest = youtube.playlists().list(parameters.get("part").toString());
            if (parameters.containsKey("mine") && parameters.get("mine") != "") {
                boolean mine = (parameters.get("mine") == "true") ? true : false;
                playlistsListMineRequest.setMine(mine);
            }

            if (parameters.containsKey("maxResults")) {
                playlistsListMineRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("onBehalfOfContentOwner") && parameters.get("onBehalfOfContentOwner") != "") {
                playlistsListMineRequest.setOnBehalfOfContentOwner(parameters.get("onBehalfOfContentOwner").toString());
            }

            if (parameters.containsKey("onBehalfOfContentOwnerChannel") && parameters.get("onBehalfOfContentOwnerChannel") != "") {
                playlistsListMineRequest.setOnBehalfOfContentOwnerChannel(parameters.get("onBehalfOfContentOwnerChannel").toString());
            }
            PlaylistListResponse response = playlistsListMineRequest.execute();
            System.out.println(response);

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
