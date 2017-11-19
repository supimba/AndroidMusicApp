package ch.hevs.denisdaniel.androidmusicapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.AlbumDao;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistDao;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.TrackDao;

// TODO Have add new Entities Album.class
@Database(entities = {Artist.class, Album.class, Track.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static final String DB_NAME = "appdatabase";
    public abstract ArtistDao artistDao();
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    };
}