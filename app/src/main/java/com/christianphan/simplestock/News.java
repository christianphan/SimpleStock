package com.christianphan.simplestock;


public class News {

    private String title;
    private String date;
    private String link;

    public News(String title, String link, String date)
    {
        this.title = title;
        this.date = date;
        this.link = link;

    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}
