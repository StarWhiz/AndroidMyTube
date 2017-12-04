package dbzgroup.mytube;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dbzgroup.mytube.Model.MyVideo;

/**
 * Created by Froz on 12/3/2017.
 */

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

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
            query.setMaxResults((long)15);
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




                //myVideo.setPubDate("12/04/2017"); // Temporary solution

                /**
                 * This function is needed to get ViewCount and Other Statistics
                 */
                YouTube.Videos.List list = youtube.videos().list("statistics");
                list.setId(result.getId().getVideoId());
                list.setKey(KEY);
                Video v = list.execute().getItems().get(0);



                myVideo.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                myVideo.setNumberOfViews(v.getStatistics().getViewCount().toString());
                //myVideo.setPubDate(v.getStatus().getPublishAt().toString());
                //myVideo.setPubDate(v.getRecordingDetails().getRecordingDate().toString());
                //System.out.println("DATES: " + result.getSnippet().
                myVideo.setPubDate(result.getSnippet().getPublishedAt().toString());


                items.add(myVideo);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
            return null;
        }
    }

    public void setOtherFields() {

    }
}