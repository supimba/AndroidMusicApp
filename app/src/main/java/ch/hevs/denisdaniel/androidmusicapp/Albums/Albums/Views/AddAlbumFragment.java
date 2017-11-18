package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.AlbumTask;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.MainActivity;
import ch.hevs.denisdaniel.androidmusicapp.R;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class AddAlbumFragment extends Fragment {
    private AppDatabase db;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_add, container, false);

        final int[] nbTrack = {0};

        ImageButton addTrack = view.findViewById(R.id.addTrack);
        Button addAlbum = view.findViewById(R.id.buttonAddAlbum);

        final LinearLayout mainLayout = view.findViewById(R.id.album_tracks);
        addTrack.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                View newLL = inflater.inflate(R.layout.track_add, container, false);
                newLL.setId(nbTrack[0]);
                nbTrack[0]++;
                mainLayout.addView(newLL);
            }
        });

        return view;
    }

}

