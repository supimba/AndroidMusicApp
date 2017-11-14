package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class AddAlbumFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_album, container, false);

    }

/*
    public void addAlbum(View view)
    {
        EditText editTextname = (EditText)findViewById(R.id.albumTitle);


        String albumTitle = editTextname.getText().toString();
        EditText editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        String artistDescription = editTextDescription.getText().toString();
        final Album newArtist = new Artist(artistName,artistDescription);
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
    }*/


}

