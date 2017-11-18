package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.AlbumTask;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class AlbumsFragment extends Fragment {

    private AppDatabase db;

    void deleteArtist(int id)
    {
        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        new AlbumTask(db, "delete",id).execute();
        changeFragment(new AlbumsFragment());


    }

    public void changeFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public AlbumsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums_list, container, false);

        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        ArrayList<Album> data = null;
    /*    ArrayList<Album> data = new ArrayList<>();
        Album a1 = new Album("Somewhere in Time");
        Album a2 = new Album("Evil Empire");
        Album a3 = new Album("Kill Em All");

        data.add(a1);
        data.add(a2);
        data.add(a3);
*/
        try {
            data = (ArrayList) new AlbumTask(db, "getAll", 0).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        BindDictionary<Album> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.albumTitle, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return album.getTitle();
            }
        });
        dictionary.addStringField(R.id.albumDescription, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return ""+ album.getRating();
            }
        });
        dictionary.addStringField(R.id.albumID, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return String.valueOf(album.getUid());
            }
        });

        FunDapter adapter = new FunDapter(AlbumsFragment.this.getActivity(), data, R.layout.fragment_albums, dictionary) ;

        ListView album_listView = (ListView) view.findViewById(R.id.lvData);
        album_listView.setAdapter(adapter);

        album_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int pos, long id) {

                TextView albumIdTextView = (TextView) v.findViewById(R.id.albumID);

                final int AlbumId = Integer.parseInt(albumIdTextView.getText().toString());

                Album album = new Album("","",0, "" );

                try {
                    album = (Album) new AlbumTask(db, "get", AlbumId).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.album_popup);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button deleteButton = alertDialog.findViewById(R.id.deleteAlbumButton);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteArtist(AlbumId);
                        alertDialog.hide();
                    }
                });


                return true;
            }

            ;
        });

        // Inflate the layout for this fragment
        return view;

    }
}
