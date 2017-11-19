package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


/**
 * Created by Denis Woeffray on 03.11.2017.
 */
@Dao
public interface TrackDao {
    @Query("SELECT * FROM tracks ORDER BY name")
    List<Track> getAll();

    @Query("SELECT * FROM tracks WHERE tracks.album_id = :album_id")
    List<Track> getAlbumTracks(Long album_id);

    @Query("SELECT * FROM tracks WHERE tracks.uid = :uid")
    Track get(Long uid);

    @Insert
    Long add(Track artist);

    @Query("DELETE FROM tracks")
    void deleteAll();

    @Delete
    void delete(Track track);

    @Update
    void update(Track track);

    @Query("SELECT * FROM tracks WHERE name LIKE :searchTerm ORDER BY name")
    List<Track> search(String searchTerm);
}
