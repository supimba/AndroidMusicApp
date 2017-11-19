package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by dnlro on 17/11/2017.
 */

public class AlbumTask extends AsyncTask<Void, Void, Object> {
    AppDatabase db;
    String task;
    Object o;
    Album album;

    public AlbumTask(AppDatabase db, String task, Object o) {
        super();
        this.db = db;
        this.task = task;
        this.o = o;
    }


    @Override
    protected Object doInBackground(Void... voids) {
        switch (task) {
            case "add":
                return db.albumDao().add((Album)o);
            case "getAll":
                return db.albumDao().getAll();
            case "get":
                return db.albumDao().get(Long.parseLong((String.valueOf(o))));
            case "delete":
                album = db.albumDao().get(Long.parseLong((String.valueOf(o))));
                db.albumDao().delete(album);
                return null;
            case "update":
                album = db.albumDao().get(Long.parseLong((String.valueOf(o))));
                db.albumDao().update(album);
            case "search":
                return db.artistDao().search((String) o);
        }
        return null;

    }
}
