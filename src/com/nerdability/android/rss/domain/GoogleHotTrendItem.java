package com.nerdability.android.rss.domain;

import java.io.Serializable;

/**
 * Created by Hill on 15/9/17.
 */
public class GoogleHotTrendItem implements Serializable{

    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String picture;
    private String picture_source;
    private String approx_traffic;
    private String news_item_title;
    private String news_item_snippet;
    private String news_item_source;
    private String news_item_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture_source() {
        return picture_source;
    }

    public void setPicture_source(String picture_source) {
        this.picture_source = picture_source;
    }

    public String getApprox_traffic() {
        return approx_traffic;
    }

    public void setApprox_traffic(String approx_traffic) {
        this.approx_traffic = approx_traffic;
    }

    public String getNews_item_title() {
        return news_item_title;
    }

    public void setNews_item_title(String news_item_title) {
        this.news_item_title = news_item_title;
    }

    public String getNews_item_snippet() {
        return news_item_snippet;
    }

    public void setNews_item_snippet(String news_item_snippet) {
        this.news_item_snippet = news_item_snippet;
    }

    public String getNews_item_source() {
        return news_item_source;
    }

    public void setNews_item_source(String news_item_source) {
        this.news_item_source = news_item_source;
    }

    public String getNews_item_url() {
        return news_item_url;
    }

    public void setNews_item_url(String news_item_url) {
        this.news_item_url = news_item_url;
    }


    @Override
    public String toString() {
        return "GoogleHotTrendItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", picture='" + picture + '\'' +
                ", picture_source='" + picture_source + '\'' +
                ", approx_traffic='" + approx_traffic + '\'' +
                ", news_item_title='" + news_item_title + '\'' +
                ", news_item_snippet='" + news_item_snippet + '\'' +
                ", news_item_source='" + news_item_source + '\'' +
                ", news_item_url='" + news_item_url + '\'' +
                '}';
    }
}
