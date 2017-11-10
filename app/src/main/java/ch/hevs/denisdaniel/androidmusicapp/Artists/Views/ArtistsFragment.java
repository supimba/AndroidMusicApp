package ch.hevs.denisdaniel.androidmusicapp.Artists.Views;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.database.Observable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;

import ch.hevs.denisdaniel.androidmusicapp.Album;
import ch.hevs.denisdaniel.androidmusicapp.AlbumsFragment;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistDao;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class ArtistsFragment extends Fragment {
    private AppDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artists_list, container, false);
        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        ArrayList<Artist> data = null;

        try {
            data = new ArtistsLoader(db).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i=0;i<data.size(); i++)
        {
            Log.d("Artist "+i, data.get(i).getName());
        }

        BindDictionary<Artist> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.textViewName, new StringExtractor<Artist>() {
            @Override
            public String getStringValue(Artist artist, int position) {
                return artist.getName();
            }
        });


        FunDapter adapter = new FunDapter(ArtistsFragment.this.getActivity(), (ArrayList<Artist>)data, R.layout.fragment_artists, dictionary) ;
        ListView artist_listview = (ListView) view.findViewById(R.id.artist_listview);
        artist_listview.setAdapter(adapter);
        return view;

    }

    private class ArtistsLoader extends AsyncTask<Void, Void, ArrayList<Artist>> {

        AppDatabase db;

        public ArtistsLoader(AppDatabase db) {
            super();
            this.db = db;
        }
        protected ArrayList<Artist> doInBackground(Void... params) {
            return getArtists(new ArrayList<Artist>());
        }
        protected void onPostExecute(Void result) {
        }

    }

    private ArrayList<Artist> getArtists(ArrayList<Artist> artists) {
        return (ArrayList<Artist>)db.artistDao().getAll();
    }


}
