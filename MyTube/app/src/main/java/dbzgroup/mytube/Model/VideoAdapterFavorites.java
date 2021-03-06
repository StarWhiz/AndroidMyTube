package dbzgroup.mytube.Model;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.youtube.YouTube;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dbzgroup.mytube.PlayerActivity;
import dbzgroup.mytube.R;

/**
 * Created by Froz on 12/3/2017.
 */

public class VideoAdapterFavorites extends RecyclerView.Adapter<VideoAdapterFavorites.VideoViewHolder> {

    private Context mCtx;
    private List<MyVideo> myVideoList;
    private static YouTube youtube; //global instance of youtube object

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private HashMap<String, String> childKey_list = new HashMap<String, String>();

    public VideoAdapterFavorites(Context mCtx, List<MyVideo> myVideoList) {
        this.mCtx = mCtx;
        this.myVideoList = myVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.video_card_view, null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        MyVideo myVideo = myVideoList.get(position);
        holder.favButton.setImageResource(R.drawable.ic_delete_black_24dp);
        holder.videoTitle.setText(myVideo.getTitle());
        holder.videoPubDate.setText("Published Date " + myVideo.getPubDate());
        holder.videoViewCount.setText("Views " + myVideo.getNumberOfViews());
        Picasso.with(mCtx).load(myVideo.getThumbnailURL()).into(holder.videoThumbnail);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        holder.setVideoIDForPlayer(myVideo.getVideoID().toString());
        holder.setFavoriteListener(position);
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
            favButton = itemView.findViewById(R.id.starButton);
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
         * Favorite button now removes video from list
         */
        public void setFavoriteListener(final int position) {
            //favButton = itemView.findViewById(R.id.starButton);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: REMOVE FUNCTION HERE
                    MyVideo myVideo = myVideoList.get(position); //now remove this part from the firebase database
                    deleteVideoFromDB(mDatabase.child(user.getUid()).child("favorites"), myVideo); // deletes the video selected from the database
                    myVideoList.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(mCtx, "Video Deleted From Playlist...", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * Given a video deletes video from database....
     * @param db
     */
    private synchronized void deleteVideoFromDB (DatabaseReference db, final MyVideo video) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while(dataSnapshots.hasNext()) {
                    DataSnapshot videoItem = dataSnapshots.next();
                    MyVideo video = videoItem.getValue(MyVideo.class); // This is the request object.
                    childKey_list.put(video.getVideoID(), videoItem.getKey());
                }
                //System.out.println("Child Key list is: " + childKey_list); // YES
                String vidToDelete = video.getVideoID();
                if (childKey_list.get(vidToDelete) != null) {
                    String childIDfound = childKey_list.get(vidToDelete);
                    mDatabase.child(user.getUid()).child("favorites").child(childIDfound).setValue(null);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}


