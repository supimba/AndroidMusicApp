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
    private String albumId;
    private Album album;
    private Artist artist;
    private ArrayList<Track> dataTracks;
    private FunDapter adapter;
    private String artistId;


    public static AlbumDetailsFragment newInstance(Album album) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();

        Bundle args = new Bundle();
        //TODO change
        // args.putLong(ALBUM_ID, album.getUid());

        fragment.setArguments(args);
        return fragment;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataTracks = new ArrayList<Track>();
        /*if (getArguments() != null)
            albumId = getArguments().getLong(ALBUM_ID);
*/


        ArrayList<String> trackIdsList = new ArrayList<>();
        albumId = album.getUid();

        // get artist entity from album
        artistId = album.getArtistId();
        final ArrayList<Artist> artistList = new ArrayList<>();

        // get all track ids from album
        getTracksList(albumId);
       // getArtistData(artistId);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.album_details, container, false);


        //todo replace
        //db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
//TODO change
        //  Long artistId = album.getArtistId();

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

        FirebaseDatabase
                .getInstance()
                .getReference("artists")
                .child(artistId)
                .child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String artistNameValue =dataSnapshot.getValue(String.class);
                        TextView artistName = view.findViewById(R.id.detailsArtistName);
                        artistName.setText(artistNameValue);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

          /*  TextView artistName = view.findViewById(R.id.detailsArtistName);
            artistName.setText(artist.getName());

*/
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


        adapter = new FunDapter(AlbumDetailsFragment.this.getActivity(), (ArrayList<Track>) dataTracks, R.layout.tracks_list_item, dictionary);
        ListView tracks_listview = (ListView) view.findViewById(R.id.track_listview);
        tracks_listview.setAdapter(adapter);
        /* fin creation des listviews*/
        return view;
    }

    private Track toTrack(DataSnapshot snapshot) {
        Track tracks = new Track();

        Track entity = snapshot.getValue(Track.class);
        entity.setUid(snapshot.getKey());

        return entity;

    }



    private void getTracksData(final ArrayList<String> trackIdsList) {
        dataTracks.clear();
        for (String track : trackIdsList) {

            FirebaseDatabase.getInstance().getReference("tracks")
                    .child(track)
                    .addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        dataTracks.add(toTrack(dataSnapshot));
                                        adapter.updateData(dataTracks);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );
        }


    }


    private List<Artist> toArtists(DataSnapshot snapshot) {
        List<Artist> artists = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Artist entity = childSnapshot.getValue(Artist.class);
            entity.setUid(childSnapshot.getKey());
            artists.add(entity);
        }
        return artists;

    }

    private void getTracksList(final String albumId) {
        final ArrayList<String> trackIdsList = new ArrayList<>();

        FirebaseDatabase.getInstance()
                .getReference("albums")
                .child(albumId)
                .child("tracks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            trackIdsList.clear();
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                trackIdsList.add(childDataSnapshot.getKey());

                            }

                            getTracksData(trackIdsList);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    private void getArtistData(String artistId){
        final ArrayList<Artist> artistList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("artists")

                .child(artistId)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            artistList.clear();
                            artist = dataSnapshot.getValue(Artist.class);
                            artist.setUid(dataSnapshot.getKey());
                            artistList.add(artist);


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
