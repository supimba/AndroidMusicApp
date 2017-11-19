package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;

import static android.arch.persistence.room.ForeignKey.CASCADE;

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


@Entity(foreignKeys = @ForeignKey(  entity = Artist.class,
                                    parentColumns = "uid",
                                    childColumns = "uid",
                                    onDelete = CASCADE
    ))
public class Album {


    @PrimaryKey(autoGenerate = true)
    private Long uid;

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

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "releasedate")
    private String releasedate;

    @ColumnInfo(name = "user_id")
    private Long userId;
    /*@ColumnInfo(name = "rating")
    private int rating;*/

    /*@ColumnInfo(name = "img_path")
    private String img_path;*/


 /*   @Embedded
    private Track tracks;
    */

    public Album(String title, String releasedate, String description /* int rating, String img_path*/) {
        this.uid = uid;
        this.title = title;
        this.description = title;
        this.releasedate = releasedate;
        /*this.rating = rating;*/
        /*this.img_path = img_path;*/
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

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

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Album{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releasedate='" + releasedate + '\'' +
                ", userId=" + userId +
                '}';
    }
}
