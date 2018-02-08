package com.example.juanlu.youtubeplayerlist.model;

/**
 * Created by juanlu on 7/02/18.
 */

public class YoutubeVideo {

    private String imageUrl;
    private String title;
    private String owner;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public YoutubeVideo(String imageUrl, String title, String owner, String url) {
        this.imageUrl = imageUrl;
        this.title = title;

        this.owner = owner;
        this.url = url;
    }

    public YoutubeVideo() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "YoutubeVideo{" +
                "title='" + title + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
