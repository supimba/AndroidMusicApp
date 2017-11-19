package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.ArtistTask;
import ch.hevs.denisdaniel.androidmusicapp.R;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.TrackTask;


public class AlbumDetailsFragment extends Fragment {
    private static final String ALBUM_ID = "albumId";
    private Long albumId ;
    private Album album;
    private Artist artist;

    private AppDatabase db;

    public static AlbumDetailsFragment newInstance(Album album) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();

        Bundle args = new Bundle();
        args.putLong(ALBUM_ID, album.getUid());

        fragment.setArguments(args);
        return fragment;
    }

    public void setAlbum(Album album){
        this.album = album;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            albumId = getArguments().getLong(ALBUM_ID);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_details, container, false);

        TextView albumTitle = view.findViewById(R.id.detailsAlbumTitle);
        albumTitle.setText(album.getTitle());


        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();

        Long artistId = album.getArtistId();
        ArrayList<Track> data = null;
        /* récupération des tracks*/
        try {
            data = (ArrayList) new TrackTask(db, "getAlbumTracks", album.getUid()).execute().get();
            artist = (Artist) new ArtistTask(db, "get", artistId).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(artist.getName() != null){
            TextView artistName = view.findViewById(R.id.detailsArtistName);
            artistName.setText(artist.getName());
        }

        /* creation des listviews*/
        BindDictionary<Track> dictionary = new BindDictionary<>();


        dictionary.addStringField(R.id.textViewName, new StringExtractor<Track>() {
            @Override
            public String getStringValue(Track track, int position) {
                return track.getName();
            }
        });
        dictionary.addStringField(R.id.textViewId, new StringExtractor<Track>() {
            @Override
            public String getStringValue(Track track, int position) {
                return String.valueOf(track.getUid());
            }
        });
        dictionary.addStringField(R.id.textViewDuration, new StringExtractor<Track>() {
            @Override
            public String getStringValue(Track track, int position) {
                return String.valueOf(track.getDuration());
            }
        });


        FunDapter adapter = new FunDapter(AlbumDetailsFragment.this.getActivity(), (ArrayList<Track>) data, R.layout.tracks_list_item, dictionary);
        ListView tracks_listview = (ListView) view.findViewById(R.id.tracks_listview);
        tracks_listview.setAdapter(adapter);
        /* fin creation des listviews*/
        return view;
    }
}
