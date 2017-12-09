package ch.hevs.denisdaniel.androidmusicapp.Artists;

/**
 * Created by Denis Woeffray on 11.11.2017.
 */
/*

public class ArtistTask extends AsyncTask<Void, Void, Object> {

    //AppDatabase db;
    String task;
    /* Object pour permettre de manipuler plus de fonctions du DAO différentes*/
    //Object o;
    /*
    public ArtistTask(AppDatabase db, String task, Object o) {
        super();
        this.db = db;
        this.task = task;
        this.o = o;
    }


    protected Object doInBackground(Void... params) {

        Recherche sur la clé de la fonction afin de savoir quelle requete effectuer
        Casting dans les "cases" afin de passer le bon type en paramètre

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
*/
