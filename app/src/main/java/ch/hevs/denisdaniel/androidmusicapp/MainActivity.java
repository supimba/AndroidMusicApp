package ch.hevs.denisdaniel.androidmusicapp;

import android.app.Activity;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.*;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistDao;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.ArtistsFragment;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.AddArtistFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppDatabase db;

    public interface OnArtistsListener {
        void onArtistsCompleted(ArrayList<Artist> artists);
        void onArtistsError(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // TODO Changer le set > add
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        switch(id)
        {
            case R.id.search:
                fragment = new SearchFragment();
                changeFragment(fragment);
                break;
            case R.id.albums:
                fragment = new AlbumsFragment();
                changeFragment(fragment);
                break;
            case R.id.artists:
                fragment = new ArtistsFragment();
                changeFragment(new ArtistsFragment());
                break;
            case R.id.tracks:
                fragment = new TracksFragment();
                changeFragment(fragment);
                break;
            case R.id.add_albums:
                fragment = new AddAlbumFragment();
                changeFragment(fragment);
                break;
            case R.id.add_artists:
                fragment = new AddArtistFragment();
                changeFragment(fragment);
                break;
            case R.id.add_tracks:
                fragment = new AddTrackFragment();
                changeFragment(fragment);
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                changeFragment(new SettingsFragment());
                break;
//            case R.id.nav_gallery:
//                //load fragment
//                break;
//            ...

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void addArtist(View view)
    {
        EditText editTextname = (EditText)findViewById(R.id.editTextName);
        String artistName = editTextname.getText().toString();
        EditText editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        String artistDescription = editTextDescription.getText().toString();

        if(artistName.equals("") || artistDescription.equals(""))
        {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill_fields, Toast.LENGTH_SHORT);
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

    public void deleteAllArtists(View view)
    {
        try
        {
            db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... voids) {
                    db.artistDao().deleteAll();
                    return null;
                }

            }.execute();
        }
        catch (Exception e)
        {
            Log.d("Exception found :",e.getMessage());
        }
    }

    public void deleteArtist(View view)
    {
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);

        new ArtistTask(db, "delete",0);
        changeFragment(new ArtistsFragment());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.artist_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                Log.i("edit","");
                return true;
            case R.id.delete:
                Log.i("delete","");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
