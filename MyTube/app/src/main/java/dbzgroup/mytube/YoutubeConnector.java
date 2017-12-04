package dbzgroup.mytube;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dbzgroup.mytube.Model.MyVideo;

/**
 * Created by Froz on 12/3/2017.
 */

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    private ImageButton thumbnailButton;

    // Your developer key goes here
    public static final String KEY = "AIzaSyAQcZqxCudMtxgUgpeSVR-xU292LAWWVXE";

    public YoutubeConnector(Context content) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(com.google.api.client.http.HttpRequest request) throws IOException {}
        }).setApplicationName(content.getString(R.string.app_name)).build();

        try{
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/description,snippet/thumbnails/default/url)");
            query.setMaxResults((long)10);
        }catch(IOException e){
            Log.d("YC", "Could not initialize: "+e);
        }
    }

    public List<MyVideo> search(String keywords){
        query.setQ(keywords);
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<MyVideo> items = new ArrayList<MyVideo>();
            for(SearchResult result:results){
                MyVideo myVideo = new MyVideo();
                myVideo.setTitle(result.getSnippet().getTitle());
                myVideo.setVideoID(result.getId().getVideoId().toString());

                /**
                 * This function is needed to get ViewCount and Other Statistics
                 */
                YouTube.Videos.List list = youtube.videos().list("statistics");
                list.setId(result.getId().getVideoId());
                list.setKey(KEY);
                Video v = list.execute().getItems().get(0);
                /**
                 * End of Function
                 */

                myVideo.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                myVideo.setNumberOfViews(v.getStatistics().getViewCount().toString());
                myVideo.setPubDate(result.getSnippet().getPublishedAt().toString());
                items.add(myVideo);

            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        }
    }

}