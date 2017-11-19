package ch.hevs.denisdaniel.androidmusicapp.Tracks;

import android.os.AsyncTask;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */



public class TrackTask extends AsyncTask<Void, Void, Object> {

    AppDatabase db;
    String task;
    /* Object pour permettre de manipuler plus de fonctions du DAO différentes*/
    Object o;

    public TrackTask(AppDatabase db, String task, Object o) {
        super();
        this.db = db;
        this.task = task;
        this.o = o;
    }

    protected Object doInBackground(Void... params) {
        /*
        Recherche sur la clé de la fonction afin de savoir quelle requete effectuer
        Casting dans les "cases" afin de passer le bon type en paramètre
         */
        switch (task) {
            case "add":
                return db.trackDao().add((Track)o);
            case "getAll":
                return db.trackDao().getAll();
            case "get":
                return db.trackDao().get(Long.parseLong((String.valueOf(o))));
            case "getAlbumTracks":
                return db.trackDao().getAlbumTracks(Long.parseLong((String.valueOf(o))));
            case "delete":
                Track track = db.trackDao().get(Long.parseLong((String.valueOf(o))));
                db.trackDao().delete(track);
                return null;
            case "update":
                db.trackDao().update((Track) o);
                break;
            case "search":
                return db.trackDao().search((String) o);
        }
        return null;
    }

    protected void onPostExecute(Void result) {
    }
}

