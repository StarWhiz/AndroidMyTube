package dbzgroup.mytube.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dbzgroup.mytube.R;

/**
 * Created by Froz on 12/3/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mCtx;
    private List<Video> videoList;

    public VideoAdapter(Context mCtx, List<Video> videoList) {
        this.mCtx = mCtx;
        this.videoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.video_card_view, null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.videoTitle.setText(video.getTitle());
        holder.videoPubDate.setText("Published " + video.getPubDate());
        holder.videoViewCount.setText("Views " + video.getNumberOfViews());

        //holder.videoThumbnail.setImageDrawable(mCtx.getResources().getDrawable(video.getThumbnailURL()));


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoTitle;
        TextView videoPubDate;
        TextView videoViewCount;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoPubDate = itemView.findViewById(R.id.videoPubDate);
            videoViewCount = itemView.findViewById(R.id.videoViewCount);

        }
    }
}
