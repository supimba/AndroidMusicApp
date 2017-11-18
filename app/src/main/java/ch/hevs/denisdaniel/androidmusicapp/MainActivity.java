package ch.hevs.denisdaniel.androidmusicapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
import android.widget.RatingBar;
import android.widget.Toast;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views.AddAlbumFragment;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views.AlbumsFragment;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.AddArtistFragment;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.ArtistsFragment;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.TrackTask;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Views.AddTrackFragment;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Views.TracksFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppDatabase db;


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

        Fragment fragment = new SearchFragment();
        changeFragment(fragment, "Search");

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

        switch(id)
        {
            case R.id.search:
                fragment = new SearchFragment();
                fragmentTitle = "search";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.albums:
                fragment = new AlbumsFragment();
                fragmentTitle = "albums";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.artists:
                fragment = new ArtistsFragment();
                fragmentTitle = "artists";
                changeFragment(new ArtistsFragment(), fragmentTitle);
                break;
            case R.id.tracks:
                fragment = new TracksFragment();
                fragmentTitle = "tracks";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.add_albums:
                fragment = new AddAlbumFragment();
                fragmentTitle = "add album";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.add_artists:
                fragment = new AddArtistFragment();
                fragmentTitle = "add artist";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.add_tracks:
                fragment = new AddTrackFragment();
                fragmentTitle = "track";
                changeFragment(fragment, fragmentTitle);
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                fragmentTitle = "settings";
                changeFragment(fragment, fragmentTitle);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment, String fragmentTitle)
    {
        if(fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
            getSupportActionBar().setTitle(fragmentTitle);
        }
    }

    public void setActionBarTitle(String title){
        setTitle(title);
    }

    public void addArtist(View view)
    {
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
        final Artist newArtist = new Artist(artistName,artistDescription);
        try
        {
            db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    db.artistDao().add(newArtist);
                    return null;
                }

            }.execute();
            Log.d("Artist added",newArtist.getName());
        }
        catch (Exception e)
        {
            Log.d("Exception found :",e.getMessage());
        }
        editTextname.setText("");
        editTextDescription.setText("");
        Toast toast = Toast.makeText(getApplicationContext(), R.string.artist_created+" : "+newArtist.getName(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteArtist(View view)
    {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);

        new ArtistTask(db, "delete",0);
        changeFragment(new ArtistsFragment(), "artist");
    }


    public void addAlbum(View view)
    {
        EditText editAlbumTitle = (EditText)findViewById(R.id.editAlbumTitle);
        String albumTitle = editAlbumTitle.getText().toString();

        EditText editAlbumReleaseDate =(EditText)findViewById(R.id.editAlbumReleaseDate);
        String albumReleaseDate = editAlbumReleaseDate.findViewById(R.id.editAlbumReleaseDate).toString();

        EditText editAlbumDescription = (EditText)findViewById(R.id.editAlbumDescription);
        String artistDescription = editAlbumDescription.getText().toString();

        RatingBar ratingBarAlbum = (RatingBar) findViewById(R.id.ratingBarAlbum);
        int ratingAlbum =ratingBarAlbum.getNumStars();

        String img_path = "drawable/musicapp_logo_black.png";

        if(albumTitle.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_field_album, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        final Album newAlbum = new Album(albumTitle,albumReleaseDate, ratingAlbum, img_path);
        try
        {
            db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    db.albumDao().add(newAlbum);
                    return null;
                }

            }.execute();
            Log.d("Artist added",newAlbum.getTitle());
        }
        catch (Exception e)
        {
            Log.d("Exception found :",e.getMessage());
        }

        editAlbumTitle.setText("");
        editAlbumDescription.setText("");


        Toast toast = Toast.makeText(getApplicationContext(), R.string.artist_created+" : "+newAlbum.getTitle(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void addTrack(View view) {
        EditText editTextname = (EditText) findViewById(R.id.editTextName);
        String trackName = editTextname.getText().toString();
        EditText editTextDuration = (EditText) findViewById(R.id.editTextDuration);
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

    public void addImageCoverAlbum(View view){

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

        /*
        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.MEDIA_IGNORE_FILENAME, ".nomedia");

            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
       // final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.add_new));

        // Add the camera options.
      //  chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
       // startActivityForResult(chooserIntent, YOUR_REQUEST_CODE);

*/
    }

    public void addArtistToAlbum(View view){
        Fragment fragment;
        String fragmentTitle;

        fragment = new AddArtistFragment();
        fragmentTitle = "add artist";
        changeFragment(fragment, fragmentTitle);

    }




}
