package ch.hevs.denisdaniel.androidmusicapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.ArtistsFragment;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class SearchFragment extends Fragment {
    private AppDatabase db;

    //define paren
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.search, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {
                List<Artist> artists = searchArtists(searchTerm);

                    BindDictionary<Artist> dictionary = new BindDictionary<>();

                    dictionary.addStringField(R.id.textViewName, new StringExtractor<Artist>() {
                        @Override
                        public String getStringValue(Artist artist, int position) {
                            return artist.getName();
                        }
                    });
                    dictionary.addStringField(R.id.textViewId, new StringExtractor<Artist>() {
                        @Override
                        public String getStringValue(Artist artist, int position) {
                            return String.valueOf(artist.getUid());
                        }
                    });

                    FunDapter adapter = new FunDapter(SearchFragment.this.getActivity(), (ArrayList<Artist>) artists, R.layout.fragment_artists, dictionary);
                    ListView artist_listview = (ListView) view.findViewById(R.id.artist_listview);
                    artist_listview.setAdapter(adapter);
                return false;
            }
        });
        return view;
    }


    public List<Artist> searchArtists(String searchTerm) {
        List<Artist> artists = new ArrayList();
        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        if(!searchTerm.equals(""))
        {
            try {
                artists = (List<Artist>) new ArtistTask(db, "search", "%"+searchTerm+"%").execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return artists;
    }



}