package dbzgroup.mytube.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.api.services.youtube.YouTube;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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
    private DatabaseReference favoritesVidsList;

    public VideoAdapterFavorites(Context mCtx, List<MyVideo> myVideoList) {
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
    }

    /**
     * Load myvideo list data structure with this function wait didn't i have this function in video adapter already hmm...
     */
    /*
    private synchronized void loadListFood() {
        adapter = new FirebaseRecyclerAdapter<Food, HistoryHolder>(Food.class,R.layout.history_layout,HistoryHolder.class,orderHistoryList.orderByChild("userId").equalTo(Common.currentUser.getId())) {
            @Override
            protected void populateViewHolder(HistoryHolder viewHolder, Food model, int position) {
                viewHolder.cart_item_name.setText(model.getName());
                viewHolder.cart_item_price.setText(model.getPrice());
                viewHolder.cart_item_quantity.setText(aOrderList.get(position).getQuantity());


                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(com.example.nguye.minisafeway.HistoryList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId",aOrderList.get(position).getFoodId()); //// bad
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
    */
}


