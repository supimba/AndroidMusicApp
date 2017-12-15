package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class AlbumsFragment extends Fragment {


    private ArrayList<Album> data = null;
    private Album selectedAlbum ;
    private final String TAG = "ALbumsFragment";
    private FirebaseDatabase database;
    private DatabaseReference ref;


    public void deleteAlbum(Long id)
    {
        //TODO Replace
        //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        //new AlbumTask(db, "delete",id).execute();


        changeFragment(new AlbumsFragment());
    }

    public void editAlbum(Album album)
    {
        AlbumEditionFragment fragment = AlbumEditionFragment.newInstance(album);

        // save the selectedalbum in main

        //TODO replace
       //((MainActivity) getActivity()).setDataObject(selectedAlbum);
        fragment.setAlbum(album);
        changeFragment(fragment);
    }

    public void showAlbum(Album album){
        AlbumDetailsFragment fragment = AlbumDetailsFragment.newInstance(album);
        fragment.setAlbum(album);
        changeFragment(fragment);

    }
    /* permet de modifier les fragments*/
    public void changeFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ref = database.getInstance().getReference();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_list, container, false);
        //TODO replace
        //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
/*
        try {
            //TODO Replace
            data = (ArrayList) new AlbumTask(db, "getAll", 0).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/

        // ajoute les données dans la listView
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
                return ""+ album.getDescription();
            }
        });
        dictionary.addStringField(R.id.albumID, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return String.valueOf(album.getUid());
            }
        });
        dictionary.addStringField(R.id.albumReleaseDate, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return String.valueOf(album.getReleasedate());
            }
        });

        FunDapter adapter = new FunDapter(AlbumsFragment.this.getActivity(), data, R.layout.albums_list_item, dictionary) ;

        ListView album_listView = (ListView) view.findViewById(R.id.lvData);
        album_listView.setAdapter(adapter);


        /*récupère les actions sur la ListView d'alum*/
        album_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           final int pos, long id) {

                TextView albumIdTextView = (TextView) v.findViewById(R.id.albumID);
                final Long AlbumId = Long.parseLong(albumIdTextView.getText().toString());

               /* try {
                   //TODO replace
                    //selectedAlbum = (Album) new AlbumTask(db, "get", AlbumId).execute().get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.albums_list_popup);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();


                Button deleteButton = alertDialog.findViewById(R.id.deleteAlbumButton);

                // action choisi
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAlbum(AlbumId);
                        alertDialog.hide();
                    }
                });

                Button editButton = alertDialog.findViewById(R.id.editAlbumButton);

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("Position1", selectedAlbum.getTitle());
                        editAlbum(selectedAlbum);
                        alertDialog.hide();
                    }
                });
                return true;
            }
        });

        album_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {

                TextView albumIdTextView = (TextView) v.findViewById(R.id.albumID);
                final int AlbumId = Integer.parseInt(albumIdTextView.getText().toString());

                //TODO replace
                /*try {
                    selectedAlbum = (Album) new AlbumTask(db, "get", AlbumId).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }*/

                showAlbum(selectedAlbum);

            }
        });




        return view;
    }
}
