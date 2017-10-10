package pt.marciopinto.projecto_final;


import java.io.Serializable;

public class Song implements Serializable {

    private int id;
    private String title;
    private String artist;
    private String duration;
    private String thumbUrl;
    private String lyrics;

    public Song() {
    }

    public Song(int id, String title, String artist, String duration, String thumbUrl, String lyrics) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.thumbUrl = thumbUrl;
        this.lyrics = lyrics;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }
}
