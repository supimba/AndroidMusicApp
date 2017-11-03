package ch.hevs.denisdaniel.androidmusicapp;

/**
 * Created by Denis Woeffray on 03.11.2017.
 */
import android.app.Application;
        import android.arch.persistence.room.Room;
        import android.content.SharedPreferences;

import ch.hevs.denisdaniel.androidmusicapp.DB.AppDatabase;

/**
 * Created by gonzalo on 7/14/17
 */

public class App extends Application {

    public static App INSTANCE;
    private static final String DATABASE_NAME = "MyDatabase";
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";

    private AppDatabase database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        INSTANCE = this;
    }

    public AppDatabase getDB() {
        return database;
    }

    public boolean isForceUpdate() {
        return getSP().getBoolean(KEY_FORCE_UPDATE, true);
    }

    public void setForceUpdate(boolean force) {
        SharedPreferences.Editor edit = getSP().edit();
        edit.putBoolean(KEY_FORCE_UPDATE, force);
        edit.apply();
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }
}