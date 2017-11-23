package ch.hevs.denisdaniel.androidmusicapp;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.AlbumTask;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views.AddAlbumFragment;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views.AlbumsFragment;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.AddArtistFragment;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.ArtistsFragment;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.TrackTask;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Views.TracksFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppDatabase db;
    private Object object;
    public static String PREFS_LANGUAGE = "Language";
    private final String BACK_STACK_ROOT_TAG = "SEARCHFRAGMENT";
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Ni");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

/*
        Fragment fragment = new SearchFragment();
        String fragmentTag = "SearchFragment";
        changeFragment(fragment, fragmentTag);
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment, BACK_STACK_ROOT_TAG).commit();
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String fragmentTitle = null;
        String fragmentTag  = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        switch(id)
        {
            case R.id.search:
                fragment = new SearchFragment();
                fragmentTag = "SearchFragment";
                changeFragment(fragment, fragmentTag);
                break;
            case R.id.albums:
                fragment = new AlbumsFragment();
                fragmentTag = "AlbumsFragment";
                changeFragment(fragment, fragmentTag);
                break;
            case R.id.artists:
                fragment = new ArtistsFragment();
                fragmentTag = "ArtistsFragment";
                changeFragment(new ArtistsFragment(), fragmentTag);
                break;
            case R.id.tracks:
                fragment = new TracksFragment();
                fragmentTag = "TracksFragment";
                changeFragment(fragment, fragmentTag);
                break;
            case R.id.add_albums:
                fragment = new AddAlbumFragment();
                fragmentTag = "AddAlbumFragment";
                changeFragment(fragment, fragmentTag);
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                fragmentTag = "SettingsFragment";
                changeFragment(fragment, fragmentTag);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment, String fragmentTag)
    {
        if(fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.addToBackStack(BACK_STACK_ROOT_TAG);
            ft.commit();

        }
    }

    public void setActionBarTitle(String title){
        setTitle(title);
    }

    public void addArtist(View view)
    {



        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        EditText editTextname = (EditText)findViewById(R.id.editTextName);
        String artistName = editTextname.getText().toString();
        EditText editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        String artistDescription = editTextDescription.getText().toString();

        if(artistName.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_artist, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Artist artist = new Artist(artistName,artistDescription);
        new ArtistTask(db,"add",artist);
        editTextname.setText("");
        editTextDescription.setText("");
        Toast toast = Toast.makeText(getApplicationContext(), R.string.artist_created+" : "+artist.getName(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteArtist(View view)
    {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        new ArtistTask(db, "delete",0);
        changeFragment(new ArtistsFragment(), "artist");
    }

    public void addAlbum(View view) throws ExecutionException, InterruptedException {

        // initiate database
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();

        // get album's data
        String albumTitle = ((EditText)findViewById(R.id.albumTitle)).getText().toString();
        String albumDescription = ((EditText)findViewById(R.id.albumDescription)).getText().toString();
        String albumReleaseDate = ((EditText)findViewById(R.id.albumReleaseDate)).getText().toString();

        // get artist's data
        String artistName = ((EditText)findViewById(R.id.artistName)).getText().toString();
        String artistDescription = ((EditText)findViewById(R.id.artistDescription)).getText().toString();

        // get tracks layout
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.album_tracks);

        if(albumTitle.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_album, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(artistName.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_artist, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Artist artist = new Artist(artistName, artistDescription);
        Album album = new Album(albumTitle,albumReleaseDate, albumDescription);
        Long artistId = (Long)new ArtistTask(db, "add", artist).execute().get();
        album.setArtistId(artistId);
        Long albumId = (Long)new AlbumTask(db,"add",album).execute().get();
        if(mainLayout!=null)
        {
            for (int i = 0;i<mainLayout.getChildCount();i++)
            {
                EditText editTrackName = (EditText)mainLayout.getChildAt(i).findViewById(R.id.trackName);
                EditText editTrackDuration = (EditText)mainLayout.getChildAt(i).findViewById(R.id.trackDuration);
                String name = editTrackName.getText().toString();
                String duration = editTrackDuration.getText().toString();
                Track track = new Track(name,duration);
                track.setAlbumId(albumId);
                new TrackTask(db,"add",track).execute().get();
            }
        }

        Toast toast = Toast.makeText(getApplicationContext(), R.string.album_created+" : "+album.getTitle(), Toast.LENGTH_SHORT);
        toast.show();

        // reset form fields
        ((EditText)findViewById(R.id.albumTitle)).setText("");
        ((EditText)findViewById(R.id.albumDescription)).setText("");
        ((EditText)findViewById(R.id.albumReleaseDate)).setText("");
        ((EditText)findViewById(R.id.artistName)).setText("");
        ((EditText)findViewById(R.id.artistDescription)).setText("");

        Fragment fragment = new AlbumsFragment();
        String fragmentTag  = "AddAlbumFragment";
        changeFragment(fragment, fragmentTag);

    }

    public void udpateAlbum(View view) throws ExecutionException, InterruptedException {

        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();

        EditText editAlbumId = (EditText)findViewById(R.id.albumID);
        String albumId = editAlbumId.getText().toString();

        EditText albumTitleEdit = (EditText)findViewById(R.id.editAlbumTitle);
        String albumTitle = albumTitleEdit.getText().toString();

        EditText editAlbumReleaseDate =(EditText)findViewById(R.id.editAlbumReleaseDateEdit);
        String albumReleaseDate = editAlbumReleaseDate.getText().toString();

        EditText editAlbumDescription = (EditText)findViewById(R.id.editAlbumDescriptionEdit);
        String artistDescription = editAlbumDescription.getText().toString();

        Album album = (Album)new AlbumTask(db, "get", albumId).execute().get();
/*        final  Album album;
*/
        //album = (Album) getDataObject();
        album.setTitle(albumTitle);
        album.setReleasedate(albumReleaseDate);
        album.setDescription(artistDescription);


        if(albumTitle.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_album, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        new AlbumTask(db,"update",album).execute().get();


changeFragment(new AlbumsFragment(), "Album");
    }

    public void updateArtist(View view) throws ExecutionException, InterruptedException {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();

        EditText editArtistId = (EditText) findViewById(R.id.editArtistId);
        String artistId = editArtistId.getText().toString();
        EditText editArtistName = (EditText) findViewById(R.id.editArtistName);
        String artistName = editArtistName.getText().toString();

        EditText editArtistDescription = (EditText) findViewById(R.id.editArtistDescription);
        String artistDescription = editArtistDescription.getText().toString();

        Artist artist = (Artist)new ArtistTask(db, "get", artistId).execute().get();

        artist.setName(artistName);
        artist.setDescription(artistDescription);

        new ArtistTask(db,"update",artist).execute().get();

    }

    public void updateTrack(View view) throws ExecutionException, InterruptedException {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();

        EditText editTrackId = (EditText) findViewById(R.id.editTrackId);
        String trackId = editTrackId.getText().toString();
        EditText editTrackName = (EditText) findViewById(R.id.editTrackName);
        String trackName = editTrackName.getText().toString();
        EditText editTrackDuration = (EditText) findViewById(R.id.editTrackDuration);
        String trackDuration = editTrackDuration.getText().toString();

        Track track = (Track) new TrackTask(db, "get", trackId).execute().get();

        track.setName(trackName);
        track.setDuration(trackDuration);

        new TrackTask(db,"update",track).execute().get();
    }


    public void addTrack(View view) {
        EditText editTextname = (EditText) findViewById(R.id.trackName);
        String trackName = editTextname.getText().toString();
        EditText editTextDuration = (EditText) findViewById(R.id.trackDuration);
        String trackDuration = editTextDuration.getText().toString();

        if (trackName.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_artist, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        final Track newTrack = new Track(trackName, trackDuration);
        try {
            db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    db.trackDao().add(newTrack);
                    return null;
                }

            }.execute();
            Log.d("Track added", newTrack.getName());
        } catch (Exception e) {
            Log.d("Exception found :", e.getMessage());
        }
        editTextname.setText("");
        editTextDuration.setText("");
        Toast toast = Toast.makeText(getApplicationContext(), R.string.artist_created + " : " + newTrack.getName(), Toast.LENGTH_SHORT);
        toast.show();
    }


    public void deleteTrack(View view)
    {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        new TrackTask(db, "delete",0);
        changeFragment(new ArtistsFragment(), "artist");
    }

   public void setDataObject(Object o ){
        this.object = o ;
   }

    public Object getDataObject(){
        return this.object;
    }


    public void addArtistToAlbum(View view){
        Fragment fragment;
        String fragmentTitle;
        fragment = new AddArtistFragment();
        fragmentTitle = "add artist";
        changeFragment(fragment, fragmentTitle);

    }




}
