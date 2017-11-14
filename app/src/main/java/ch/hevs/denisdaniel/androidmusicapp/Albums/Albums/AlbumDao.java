package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by dnlro on 14/11/2017.
 */

public interface AlbumDao {
    @Query("SELECT * FROM album")
    List<Album> getAll();

    @Query("SELECT * FROM album WHERE album.uid = :uid")
    Album get(int uid);

    @Insert
    void add(Album album);

    @Query("DELETE FROM album")
    void deleteAll();

    @Delete
    void delete(Album album);

    @Update
    void update(Album album);

}
