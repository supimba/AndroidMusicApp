package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */


public class ArtistTask extends AsyncTask<Void, Void, Object> {

    AppDatabase db;
    String task;
    int id;

    public ArtistTask(AppDatabase db, String task, int id) {
        super();
        this.db = db;
        this.task = task;
        this.id = id;
    }

    protected Object doInBackground(Void... params) {
        switch (task) {
            case "getAll":
                return db.artistDao().getAll();
            case "get":
                return db.artistDao().get(id);
            case "delete":
                Artist artist = db.artistDao().get(id);
                db.artistDao().delete(artist);
                return null;
        }
        return null;
    }

    protected void onPostExecute(Void result) {
    }
}

