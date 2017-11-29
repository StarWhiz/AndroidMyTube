package dbzgroup.mytube.Model;

/**
 * Created by Froz on 11/29/2017.
 */

public class VideoItem {
    private String title;
    private String numberOfViews;
    private String thumbnailURL;
    private String pubDate;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String id) {
        this.pubDate = id;
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

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

}