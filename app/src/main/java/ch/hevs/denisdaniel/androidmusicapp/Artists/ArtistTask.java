package ch.hevs.denisdaniel.androidmusicapp.Artists;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */


public class ArtistTask extends AsyncTask<Void, Void, Object> {

    AppDatabase db;
    String task;
    Object o;

    public ArtistTask(AppDatabase db, String task, Object o) {
        super();
        this.db = db;
        this.task = task;
        this.o = o;
    }

    protected Object doInBackground(Void... params) {
        switch (task) {
            case "add":
                return db.artistDao().add((Artist)o);
            case "getAll":
                return db.artistDao().getAll();
            case "get":
                return db.artistDao().get(Long.parseLong((String.valueOf(o))));
            case "delete":
                Artist artist = db.artistDao().get(Long.parseLong((String.valueOf(o))));
                db.artistDao().delete(artist);
                break;
            case "search":
                return db.artistDao().search((String) o);
            case "update":
                db.artistDao().update((Artist) o);
                break;
        }
        return null;
    }

    protected void onPostExecute(Void result) {
    }
}

