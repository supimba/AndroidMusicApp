package ch.hevs.denisdaniel.androidmusicapp.Tracks.Views;

import android.arch.persistence.room.Room;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Artist;
import ch.hevs.denisdaniel.androidmusicapp.Artists.Views.ArtistEditionFragment;
import ch.hevs.denisdaniel.androidmusicapp.MainActivity;
import ch.hevs.denisdaniel.androidmusicapp.R;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.TrackTask;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class TracksFragment extends Fragment {
    private AppDatabase db;
    private ArrayList<Track> data = null;
    private Track selectedTrack ;


    void deleteTrack(int id)
    {
        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        new TrackTask(db, "delete",id).execute();
        changeFragment(new TracksFragment());
    }

    public void changeFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tracks_list, container, false);
        db = Room.databaseBuilder(this.getActivity(), AppDatabase.class, AppDatabase.DB_NAME).build();
        ArrayList<Track> data = null;

        try {
            data = (ArrayList) new TrackTask(db, "getAll", 0).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

        FunDapter adapter = new FunDapter(TracksFragment.this.getActivity(), (ArrayList<Track>) data, R.layout.tracks_list_item, dictionary);
        ListView tracks_listview = (ListView) view.findViewById(R.id.tracks_listview);
        tracks_listview.setAdapter(adapter);


        tracks_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int pos, long id) {

                TextView editTextId = (TextView) v.findViewById(R.id.textViewId);

                final int trackId = Integer.parseInt(editTextId.getText().toString());

                try {
                    selectedTrack = (Track) new TrackTask(db, "get", trackId).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(R.layout.tracks_list_popup);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Button deleteButton = alertDialog.findViewById(R.id.deleteButton);
                Button editButton = alertDialog.findViewById(R.id.editButton);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTrack(trackId);
                        alertDialog.hide();
                    }
                });

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTrack(selectedTrack);
                        alertDialog.hide();

                    }

                });

                return true;
            }

            ;
        });
            return view;
        }
    public void editTrack(Track track)
    {
        Log.d("", "editTrack: "+track);
        TrackEditionFragment fragment = TrackEditionFragment.newInstance(track);
        ((MainActivity) getActivity()).setDataObject(selectedTrack);
        fragment.setTrack(track);
        changeFragment(fragment);
    }
}
