package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.arch.persistence.room.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */

@Entity
public class Artist {
    @Exclude
    private String uid;
    private String albumUid;
    private String name;
    private String description;

    public Artist(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    public Artist()
    {
        this.name = name;
        this.description = description;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getAlbumUid() {
        return albumUid;
    }

    public String getName() {

        if(name==null)
            return "";
        return name;
    }

    public void setAlbumUid(String albumUid) {
        this.albumUid = albumUid;
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

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("artist", name);
        result.put("description", description);
        return result;
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
