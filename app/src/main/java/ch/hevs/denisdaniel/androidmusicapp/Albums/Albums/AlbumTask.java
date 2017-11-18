package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by dnlro on 17/11/2017.
 */

public class AlbumTask extends AsyncTask<Void, Void, Object> {
    AppDatabase db;
    String task;
    Object id;

    public AlbumTask(AppDatabase db, String task, Object id) {
        super();
        this.db = db;
        this.task = task;
        this.id = id;
    }


    @Override
    protected Object doInBackground(Void... voids) {
        switch (task) {
            case "add":
                return db.albumDao().add((Album)id);
            case "getAll":
                return db.albumDao().getAll();
            case "get":
                return db.albumDao().get(Integer.parseInt((String.valueOf(id))));
            case "delete":
                Album album = db.albumDao().get(Integer.parseInt((String.valueOf(id))));
                db.albumDao().delete(album);
                return null;
        }
        return null;

    }
}
