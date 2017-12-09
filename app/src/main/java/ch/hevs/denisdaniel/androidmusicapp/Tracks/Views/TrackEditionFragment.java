package ch.hevs.denisdaniel.androidmusicapp.Tracks.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ch.hevs.denisdaniel.androidmusicapp.R;
import ch.hevs.denisdaniel.androidmusicapp.Tracks.Track;

/**
 * Created by Denis Woeffray on 19.11.2017.
 */

public class TrackEditionFragment extends Fragment {
    private int trackId;
    private Track track;

    private String newTrackTitle;
    public void setTrack(Track track){
        this.track = track;
    }

    public static TrackEditionFragment newInstance(Track track) {
        TrackEditionFragment fragment = new TrackEditionFragment();
        Bundle args = new Bundle();
        //TODO change
        if(track!=null)
           // args.putLong("TRACK_ID", track.getUid());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_edit, container, false);

        EditText editTrackId = view.findViewById(R.id.editTrackId);
        editTrackId.setText(track.getUid().toString());

        EditText editTrackDuration = view.findViewById(R.id.editTrackDuration);
        editTrackDuration.setText(track.getDuration().toString());

        EditText editTrackName = view.findViewById(R.id.editTrackName);
        editTrackName.setText(track.getName().toString());


        return view;
    }
}
