package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by dnlro on 02/11/2017.
 */

/*
@Entity(foreignKeys = @ForeignKey(onDelete = CASCADE,
        entity = Artist.class, parentColumns = "uid",
        childColumns = "uid"))

*/


/*,
        foreignKeys = @Fo
        reignKey(  entity = Artist.class,
                parentColumns = "uid",
                childColumns = "userid",
                onDelete = SET_NULL,
                onUpdate = CASCADE)
)*/
@Entity
public class Album {


    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "userid")
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "releasedate")
    private String releasedate;

    @ColumnInfo(name = "rating")
    private int rating;

    @ColumnInfo(name = "img_path")
    private String img_path;


 /*   @Embedded
    private Track tracks;
    */

    public Album(String title, String releasedate, int rating, String img_path) {
        this.uid = uid;
        this.title = title;
        this.releasedate = releasedate;
        this.rating = rating;
        this.img_path = img_path;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
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
