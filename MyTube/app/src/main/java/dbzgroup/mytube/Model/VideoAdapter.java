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
import java.util.List;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private FirebaseDatabase database;
    private DatabaseReference favoritesVidsList;

    public VideoAdapter(Context mCtx, List<MyVideo> myVideoList) {
        this.mCtx = mCtx;
        this.myVideoList = myVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.video_card_view, null);
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
        holder.setFavoriteListener(position); //pass position...
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

        /**
         * Contains one card view.
         * @param itemView
         */
        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoPubDate = itemView.findViewById(R.id.videoPubDate);
            videoViewCount = itemView.findViewById(R.id.videoViewCount);

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

        /**
         * Do Something with favorites Button.... When pressed I want to add the video favorited into a playlist called "SJSU-CMPE-137".
         * If the playlist doesn't exist it creates that playlist under the user's account.
         *
         * ADD VIDEO TO DATABASE...
         */
        public void setFavoriteListener(final int position) {
            favButton = itemView.findViewById(R.id.starButton);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!favorited) {
                        favButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                        favorited = true;
                        MyVideo myVideo = myVideoList.get(position); //now add this part into firebase database
                    }
                    else {
                        favButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        favorited = false;
                    }
                }
            });
        }
    }
}


