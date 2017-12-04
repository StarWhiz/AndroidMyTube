package dbzgroup.mytube.Model;

/**
 * Created by Froz on 11/29/2017.
 */

public class Video {
    private String title;
    private String numberOfViews;
    private String thumbnailURL;
    private String pubDate;

    public Video(String title, String numberOfViews, String thumbnailURL, String pubDate) {
        this.title = title;
        this.numberOfViews = numberOfViews;
        this.thumbnailURL = thumbnailURL;
        this.pubDate = pubDate;
    }
    public Video (){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(String numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}