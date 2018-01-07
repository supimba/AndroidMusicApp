package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */


public class Track {

    @NonNull
    private String uid;
    private String albumUid;
    private String name;
    private String duration;


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
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAlbumUid() {
        return albumUid;
    }

    public void setAlbumUid(String albumUid) {
        this.albumUid = albumUid;
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



    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("duration", duration);
        result.put("albumUid", albumUid);


        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", album_id=" + albumUid +
                '}';
    }

}
