package ch.hevs.denisdaniel.androidmusicapp;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.search, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {

                /* ajout des listview selon resultat de la recherche*/
                /* artistes*/
                List<Artist> artists = searchArtists(searchTerm);
                if (artists.size() > 0) {
                    BindDictionary<Artist> dictionaryArtist = new BindDictionary<Artist>();
                    dictionaryArtist.addStringField(R.id.textViewName, new StringExtractor<Artist>() {
                        @Override
                        public String getStringValue(Artist artist, int position) {
                            return artist.getName().toString();
                        }
                    });
                    FunDapter adapterArtists = new FunDapter(getActivity(), (ArrayList<Artist>) artists, R.layout.artists_list_item, dictionaryArtist);
                    ListView artist_listview = (ListView) view.findViewById(R.id.artists_listview);
                    artist_listview.setAdapter(adapterArtists);
                }
                /* tracks*/
                List<Track> tracks = searchTracks(searchTerm);
                if (tracks.size() > 0) {
                    BindDictionary<Track> dictionaryTracks = new BindDictionary<Track>();
                    dictionaryTracks.addStringField(R.id.textViewName, new StringExtractor<Track>() {
                        @Override
                        public String getStringValue(Track track, int position) {
                            return track.getName().toString();
                        }
                    });
                    FunDapter adapterTrack = new FunDapter(getActivity(), (ArrayList<Track>) tracks, R.layout.tracks_list_item, dictionaryTracks);
                    ListView track_listview = (ListView) view.findViewById(R.id.tracks_listview);
                    track_listview.setAdapter(adapterTrack);
                }
                /* albums*/
                List<Album> albums = searchAlbums(searchTerm);
                Log.d("", "Albums: "+albums);

                if (albums.size() > 0) {
                    BindDictionary<Album> dictionaryAlbums = new BindDictionary<Album>();
                    dictionaryAlbums.addStringField(R.id.albumTitle, new StringExtractor<Album>() {
                        @Override
                        public String getStringValue(Album album, int position) {
                            return album.getTitle().toString();
                        }
                    });
                    FunDapter adapterAlbums = new FunDapter(getActivity(), (ArrayList<Album>) albums, R.layout.albums_list_item, dictionaryAlbums);
                    ListView albums_listview = (ListView) view.findViewById(R.id.albums_listview);
                    albums_listview.setAdapter(adapterAlbums);
                }
                return false;
            }
        });
        return view;
    }

    /* classes de recherche, la même logique chaque fois*/
    public List<Artist> searchArtists(String searchTerm) {
        List<Artist> artists = new ArrayList();

        //TODO replace
       //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
       /*
        if (!searchTerm.equals("")) {
            try {
                artists = (List<Artist>) new ArtistTask(db, "search", "%" + searchTerm + "%").execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        */
        return artists;
    }

    public List<Track> searchTracks(String searchTerm) {
        List<Track> tracks = new ArrayList();
        //TODO replace
        //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        /*
        if (!searchTerm.equals("")) {
            try {
                tracks = (List<Track>) new TrackTask(db, "search", "%" + searchTerm + "%").execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        */
        return tracks;
    }

    public List<Album> searchAlbums(String searchTerm) {
        final List<Album> albums = new ArrayList<Album>();
        FirebaseDatabase.getInstance()
                .getReference("albums")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    albums.clear();
                                    albums.addAll(toAlbums(dataSnapshot));
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
        return albums;
    }
    private List<Album> toAlbums(DataSnapshot snapshot){
        List<Album> albums = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Album entity = childSnapshot.getValue(Album.class);
            entity.setUid(childSnapshot.getKey());
            albums.add(entity);
        }
        return albums;

    }

}