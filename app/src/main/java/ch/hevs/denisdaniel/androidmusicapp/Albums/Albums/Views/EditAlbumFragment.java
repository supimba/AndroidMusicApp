package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.AlbumTask;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.MainActivity;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by dnlro on 18/11/2017.
 */

public class EditAlbumFragment extends android.support.v4.app.Fragment {
    private int albumId;
    private Album album;
    private AppDatabase db;
    private String newAlbumTitle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumId = getArguments().getInt("albumId", 0);

        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();

        try {
            album = (Album) new AlbumTask(db, "get", albumId).execute().get();

            Log.i("Album title: ", album.getTitle());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_edit, container, false);

        EditText editAlbumTitle = view.findViewById(R.id.editAlbumTitleEdit);
        editAlbumTitle.setText(album.getTitle());

        newAlbumTitle = editAlbumTitle.getText().toString();


        Button buttonUpdate = view.findViewById(R.id.buttonUpdateAlbum);

        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("EditAlbumFragment", "updateAlbum");

                        album.setTitle(newAlbumTitle);
                        ((MainActivity) getActivity()).udpateAlbum(view, album);



                    }
                }
        );


        //new AlbumTask(db, "update",id).execute();

        return view;


    }


}
