package com.developer.artisla.kyrgyztimes.model;

import java.io.Serializable;

/**
 * Created by artisla on 2/24/16.
 */
public class News implements Serializable{
    private String title;
    private String header;
    private String newsUrl;
    private int comment;

    public News() {}

    public News(String title, int comment){
        this.title = title;
        this.comment = comment;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getHeader() {

        return header;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setComment(int comment){
        this.comment = comment;
    }

    public String getTitle(){
        return this.title;
    }

    public int getComment(){
        return this.comment;
    }
}
