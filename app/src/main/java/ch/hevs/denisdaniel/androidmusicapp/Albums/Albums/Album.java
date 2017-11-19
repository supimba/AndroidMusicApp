package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.SET_DEFAULT;
import static android.arch.persistence.room.ForeignKey.SET_NULL;

/**
 * Created by dnlro on 02/11/2017.
 */



@Entity(foreignKeys = @ForeignKey(  entity = Artist.class,
                                    parentColumns = "uid",
                                    childColumns = "artist_id",
                                    onDelete = CASCADE
    ))
public class Album {


    @PrimaryKey(autoGenerate = true)
    private Long uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "releasedate")
    private String releasedate;

    @ColumnInfo(name = "artist_id")
    private Long artistId;

    public Album(String title, String releasedate, String description) {
        this.uid = uid;
        this.title = title;
        this.description = description;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {

        if(this.title==null)
        {
            return "";
        }
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Album{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releasedate='" + releasedate + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
