package dbzgroup.mytube;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dbzgroup.mytube.Model.Video;

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
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            query.setMaxResults((long)25);
        }catch(IOException e){
            Log.d("YC", "Could not initialize: "+e);
        }
    }

    public List<Video> search(String keywords){
        query.setQ(keywords);
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<Video> items = new ArrayList<Video>();
            for(SearchResult result:results){
                Video video = new Video();
                video.setTitle(result.getSnippet().getTitle());
                //video.setPubDate(result.getSnippet().getPublishedAt().toString());


                System.out.println("This is the cause of my error: " + result.getSnippet().getPublishedAt());
                video.setPubDate("12/04/2017"); // Temporary solution


                /**
                 * Trying to fix problem  here
                 */
                result.getId().getVideoId(); // vid id is here
                /*
                YouTube.Videos.List list = youtube.videos().list("statistics");
                list.setId("kffacxfA7G4");
                list.setKey("your private API KEY");
                Youtube.Video v = list.execute().getItems().get(0);
                System.out.println("The view count is: "  + v.getStatistics().getViewCount());
                */
                /**
                 * Trying to fix problem  here
                 */


                video.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                video.setNumberOfViews(result.getId().getVideoId());
                items.add(video);
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