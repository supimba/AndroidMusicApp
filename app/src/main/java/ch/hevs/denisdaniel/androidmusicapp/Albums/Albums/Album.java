package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.Entity;

import java.util.Date;

/**
 * Created by dnlro on 02/11/2017.
 */

@Entity
public class Album {
    private int id;
    private String title;
    private Date releasedate;
    private int rating;
    private String img_path;

    public Album(String title) {
        this.title = title;
    }

    public Album(int id, String title, Date releasedate, int rating, String img_path) {
        this.id = id;
        this.title = title;
        this.releasedate = releasedate;
        this.rating = rating;
        this.img_path = img_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
