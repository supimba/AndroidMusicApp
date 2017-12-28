package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import ch.hevs.denisdaniel.androidmusicapp.R;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;


public class AlbumDetailsFragment extends Fragment {
    private static final String ALBUM_ID = "albumId";
    private Long albumId ;
    private Album album;
    private Artist artist;
    private ArrayList<Track> data;


    public static AlbumDetailsFragment newInstance(Album album) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();

        Bundle args = new Bundle();
       //TODO change
        // args.putLong(ALBUM_ID, album.getUid());

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

        //todo replace
        //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
//TODO change
      //  Long artistId = album.getArtistId();
        data = null;
        /* récupération des tracks*/
       //TODO replace
        /*
        try {
            data = (ArrayList) new TrackTask(db, "getAlbumTracks", album.getUid()).execute().get();
            artist = (Artist) new ArtistTask(db, "get", artistId).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        ArrayList<String> trackIdsList = new ArrayList<>();
        trackIdsList = getTracksList(album.getUid());


        FirebaseDatabase.getInstance()
                .getReference("tracks")
                .child(album.getUid())
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    data.clear();
                                    data.addAll(toAlbums(dataSnapshot));
                                  //  adapter.updateData(data);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );



        if(artist.getName() != null){
            TextView artistName = view.findViewById(R.id.detailsArtistName);
            artistName.setText(artist.getName());
        }

        /* creation des textview*/
        TextView albumTitle = view.findViewById(R.id.detailsAlbumTitle);
        albumTitle.setText(album.getTitle());

        TextView albumDescription = view.findViewById(R.id.detailsAlbumDesc);
        albumDescription.setText(album.getDescription());

        TextView releaseDate = view.findViewById(R.id.detailsReleaseDate);
        releaseDate.setText(album.getReleasedate());

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
        ListView tracks_listview = (ListView) view.findViewById(R.id.track_listview);
        tracks_listview.setAdapter(adapter);
        /* fin creation des listviews*/
        return view;
    }
    private List<Track> toAlbums(DataSnapshot snapshot){
        List<Track> tracks = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Track entity = childSnapshot.getValue(Track.class);
            entity.setUid(childSnapshot.getKey());
            tracks.add(entity);
        }
        return tracks;

    }

    private ArrayList<String> getTracksList(String albumId){
        final ArrayList<String> trackIdsList = new ArrayList<>();

        FirebaseDatabase.getInstance()
                .getReference("albums")
                .child(albumId)
                .child("tracks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        trackIdsList.clear();
                        for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                        {
                            trackIdsList.add(childDataSnapshot.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return trackIdsList;

    }
}
