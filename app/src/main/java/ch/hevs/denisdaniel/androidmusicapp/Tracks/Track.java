package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */

@Entity(tableName = "tracks",
        foreignKeys = @ForeignKey(  entity = Album.class,
                                    parentColumns = "uid",
                                    childColumns = "uid",
                                    onDelete = CASCADE,
                                    onUpdate = CASCADE)
)
public class Track {

    public Track(String name, String duration)
    {
        this.name = name;
        this.duration = duration;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "duration")
    private String duration;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
