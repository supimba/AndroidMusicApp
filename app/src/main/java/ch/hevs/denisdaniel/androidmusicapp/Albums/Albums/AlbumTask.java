package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums;

/**
 * Created by dnlro on 17/11/2017.
 */


/*
public class AlbumTask extends AsyncTask<Void, Void, Object> {
    AppDatabase db;
    String task;
    Object o;
    Album album;

    public AlbumTask(AppDatabase db, String task, Object o) {
        super();
        this.db = db;
        this.task = task;
        Object pour permettre de manipuler plus de fonctions du DAO différentes
        this.o = o;
    }
*/

/*
    @Override
    protected Object doInBackground(Void... voids) {
        /*
        Recherche sur la clé de la fonction afin de savoir quelle requete effectuer
        Casting dans les "cases" afin de passer le bon type en paramètre

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
               // album = db.albumDao().get(Long.parseLong((String.valueOf(o))));
                db.albumDao().update((Album) o);
                //db.albumDao().update(album);
                break;
            case "search":
                return db.albumDao().search((String) o);
        }
        return null;

    }
}
*/