package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;

/**
 * Created by dnlro on 18/11/2017.
 */


@Entity(tableName = "album_artist_join",
    primaryKeys = {"albumId", "artistId"},
        foreignKeys = {
                @ForeignKey(entity = Album.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Artist.class,
                        parentColumns = "id",
                        childColumns = "repoId")
        })
public class AlbumArtistJoin {


}
