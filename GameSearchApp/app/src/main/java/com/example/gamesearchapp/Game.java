package com.example.gamesearchapp;

public class Game {

    private String genre;
    private String imgURL;
    private String subgenre;
    private String title;
    private String pid;
    private String rating;
    private String rCount;

    public Game(String genre, String imgURL, String subgenre, String title, String pid, String rating, String rCount) {
        this.genre = genre;
        this.imgURL = imgURL;
        this.subgenre = subgenre;
        this.title = title;
        this.pid = pid;
        this.rating = rating;
        this.rCount = rCount;
    }

    public String getGenre() { return genre; }

    public String getImgURL() { return imgURL; }

    public String getSubgenre() { return subgenre; }

    public String getTitle() { return title; }

    public String getPid() { return pid; }

    public String getRating() { return rating; }

    public String getrCount() { return rCount; }

}
