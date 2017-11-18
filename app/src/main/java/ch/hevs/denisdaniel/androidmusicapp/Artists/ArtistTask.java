package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */


public class ArtistTask extends AsyncTask<Void, Void, Object> {

    AppDatabase db;
    String task;
    Object id;

    public ArtistTask(AppDatabase db, String task, Object id) {
        super();
        this.db = db;
        this.task = task;
        this.id = id;
    }

    protected Object doInBackground(Void... params) {
        switch (task) {
            case "add":
                return db.artistDao().add((Artist)id);
            case "getAll":
                return db.artistDao().getAll();
            case "get":
                return db.artistDao().get(Integer.parseInt((String.valueOf(id))));
            case "delete":
                Artist artist = db.artistDao().get(Integer.parseInt((String.valueOf(id))));
                db.artistDao().delete(artist);
                return null;
            case "search":
                return db.artistDao().search((String) id);
        }
        return null;
    }

    protected void onPostExecute(Void result) {
    }
}

