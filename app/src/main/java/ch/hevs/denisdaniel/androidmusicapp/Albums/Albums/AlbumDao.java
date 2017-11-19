package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;

/**
 * Created by dnlro on 14/11/2017.
 */

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album")
    List<Album> getAll();

    @Query("SELECT * FROM album WHERE album.uid = :uid")
    Album get(Long uid);

    @Insert
    Long add(Album album);
    @Insert
    void add(Album album, Artist artist);
    @Query("DELETE FROM album")
    void deleteAll();
    @Delete
    void delete(Album album);
    @Update
    void update(Album album);

    @Query("SELECT * FROM album WHERE title LIKE :searchTerm ORDER BY title")
    List<Album> search(String searchTerm);

}
