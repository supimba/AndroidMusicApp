package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnlro on 02/11/2017.
 */




public class Album {


    @NonNull
    private String uid;
    private String title;
    private String description;
    private String releasedate;
    private String artistId;

    public Album(String title, String releasedate, String description) {
        this.uid = uid;
        this.title = title;
        this.releasedate=releasedate;
        this.description = description;
    }


    public Album() {
        this.uid = uid;
        this.title = title;
        this.releasedate=releasedate;
        this.description = description;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("artistId", artistId);
        result.put("description", description);
        result.put("releasedate", releasedate);
        result.put("title", title);

        return result;
    }

}
