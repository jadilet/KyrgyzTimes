package com.developer.artisla.kyrgyztimes.model;

import java.io.Serializable;

/**
 * Created by artisla on 2/17/16.
 */
public class Videos implements Serializable {
    private String title;
    private String imagePath;
    private String videoUrl;

    public Videos() {
    }

    public Videos(String title, String imagePath, String videoUrl) {
        this.title = title;
        this.imagePath = imagePath;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
