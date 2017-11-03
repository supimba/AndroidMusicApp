package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */
@Dao
public interface ArtistDao {
    @Query("SELECT * FROM artist")
    List<Artist> getAll();

    @Insert
    void add(Artist artist);
}
