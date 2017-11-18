package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */


public class TrackTask extends AsyncTask<Void, Void, Object> {

    AppDatabase db;
    String task;
    Object id;

    public TrackTask(AppDatabase db, String task, Object id) {
        super();
        this.db = db;
        this.task = task;
        this.id = id;
    }

    protected Object doInBackground(Void... params) {
        switch (task) {
            case "getAll":
                return db.trackDao().getAll();
            case "get":
                return db.trackDao().get(Integer.parseInt((String.valueOf(id))));
            case "delete":
                Track track = db.trackDao().get(Integer.parseInt((String.valueOf(id))));
                db.trackDao().delete(track);
                return null;
            case "search":
                return db.trackDao().search((String) id);
        }
        return null;
    }

    protected void onPostExecute(Void result) {
    }
}

