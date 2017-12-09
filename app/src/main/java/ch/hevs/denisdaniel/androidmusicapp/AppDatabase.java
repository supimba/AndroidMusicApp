package ch.hevs.denisdaniel.androidmusicapp;

// TODO Have add new Entities Album.class

/*
@Database(entities = {Artist.class, Album.class, Track.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static final String DB_NAME = "appdatabase";
    public abstract ArtistDao artistDao();
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    };
}
*/