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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.MainActivity;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class AlbumsFragment extends Fragment {


    private ArrayList<Album> data;
    private Album selectedAlbum;
    private final String TAG = "ALbumsFragment";
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FunDapter adapter;

    //Long id
    public void deleteAlbumProc(final Album album) {
        //TODO Replace -> OK && Delete

        // get all tracks in a list
        final ArrayList<String> trackIdsList = new ArrayList<>();

        String albumId = album.getUid();

        FirebaseDatabase.getInstance()
                .getReference("albums")
                .child(albumId)
                .child("tracks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        trackIdsList.clear();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            trackIdsList.add(childDataSnapshot.getKey());
                        }
                        deleteAlbumDependencies(trackIdsList);
                        deleteAlbum(album);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




    }

    private void deleteAlbum(final Album album) {
        FirebaseDatabase.getInstance()
                .getReference("albums")
                .child(album.getUid())
                .removeValue(new DatabaseReference.CompletionListener() {

                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d(TAG, "Delete failure!", databaseError.toException());
                        } else {
                            Log.d(TAG, "Delete successful!");
                            data.remove(album);

                        }
                    }
                });
        changeFragment(new AlbumsFragment());
    }

    //TODO recupérer les ids de l'album dans firebase avant de le supprimer
    private void deleteAlbumDependencies(ArrayList<String> trackIds) {

        for (String t : trackIds) {

            FirebaseDatabase.getInstance()
                    .getReference("tracks")
                    .child(t)
                    .removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d(TAG, "Delete failure!", databaseError.toException());
                            } else {
                                Log.d(TAG, "Delete successful!");
                            }
                        }
                    });
        }


    }

    public void editAlbum(Album album) {
        AlbumEditionFragment fragment = AlbumEditionFragment.newInstance(album);

        // save the selectedalbum in main

        //TODO replace
        ((MainActivity) getActivity()).setDataObject(selectedAlbum);
        fragment.setAlbum(album);
        changeFragment(fragment);
    }

    public void showAlbum(Album album) {
        AlbumDetailsFragment fragment = AlbumDetailsFragment.newInstance(album);
        fragment.setAlbum(album);
        changeFragment(fragment);

    }

    /* permet de modifier les fragments*/
    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack(TAG);
        ft.commit();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new ArrayList<Album>();

        FirebaseDatabase.getInstance()
                .getReference("albums")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    data.clear();
                                    data.addAll(toAlbums(dataSnapshot));
                                    adapter.updateData(data);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }

    private List<String> tracksToDelete(DataSnapshot snapshot) {
        List<String> tracks = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

            //Track entity = childSnapshot.getValue(Track.class);
            //entity.setUid(childSnapshot.getKey());

            tracks.add(childSnapshot.toString());
        }
        return tracks;

    }

    private List<Album> toAlbums(DataSnapshot snapshot) {
        List<Album> albums = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Album entity = childSnapshot.getValue(Album.class);
            entity.setUid(childSnapshot.getKey());
            albums.add(entity);
        }
        return albums;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_list, container, false);
        //TODO replace with Firebase ->OK

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
                return "" + album.getDescription();
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

        adapter = new FunDapter(AlbumsFragment.this.getActivity(), data, R.layout.albums_list_item, dictionary);

        ListView album_listView = (ListView) view.findViewById(R.id.lvData);
        album_listView.setAdapter(adapter);


        /*récupère les actions sur la ListView d'alum*/
        album_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           final int pos, long id) {

                TextView albumIdTextView = (TextView) v.findViewById(R.id.albumID);

                //TODO replace with Firebase -> OK
                selectedAlbum = data.get(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.albums_list_popup);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();


                final Button deleteButton = alertDialog.findViewById(R.id.deleteAlbumButton);

                // action choisi
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //deleteAlbum(AlbumId);
                        deleteAlbumProc(selectedAlbum);
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

                //TODO replace with FB -> OK

                selectedAlbum = data.get(i);
                showAlbum(selectedAlbum);

            }
        });
        return view;
    }
}
