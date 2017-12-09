package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */


public class Track {
    public Track(String name, String duration)
    {
        this.name = name;
        this.duration = duration;
    }
    public Track()
    {
        this.name = name;
        this.duration = duration;
    }

    @Exclude
    private String uid;
    private String name;
    private String duration;
    private String albumId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("albumUID", albumId);
        result.put("trackTitle", name);
        result.put("duration", duration);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", album_id=" + albumId +
                '}';
    }
}
