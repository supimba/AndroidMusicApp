package ch.hevs.denisdaniel.androidmusicapp.Artists;

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
public interface ArtistDao {
    @Query("SELECT * FROM artist ORDER BY name")
    List<Artist> getAll();

    @Query("SELECT * FROM artist WHERE artist.uid = :uid")
    Artist get(Long uid);

    @Insert
    Long add(Artist artist);

    @Query("DELETE FROM artist")
    void deleteAll();

    @Delete
    void delete(Artist artist);

    @Update
    void update(Artist artist);

    @Query("SELECT * FROM artist WHERE name LIKE :searchTerm ORDER BY name")
    List<Artist> search(String searchTerm);
}
