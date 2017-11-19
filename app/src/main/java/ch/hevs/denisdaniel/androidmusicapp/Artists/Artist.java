package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */

@Entity
public class Artist {

    public Artist(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    @PrimaryKey(autoGenerate = true)
    private Long uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {

        if(name==null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
