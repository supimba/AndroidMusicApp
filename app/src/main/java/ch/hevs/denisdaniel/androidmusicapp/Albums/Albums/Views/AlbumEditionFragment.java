package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by dnlro on 18/11/2017.
 */

public class AlbumEditionFragment extends android.support.v4.app.Fragment {
    private EditText releaseDate;
    private Album album;
    private AppDatabase db;
    private String newAlbumTitle;
    public void setAlbum(Album album){
        this.album = album;
    }
    public static AlbumEditionFragment newInstance(Album album) {
        AlbumEditionFragment fragment = new AlbumEditionFragment();
        Bundle args = new Bundle();
        if(album!=null)
            args.putLong("ALBUM_ID", album.getUid());

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_edit, container, false);

        EditText albumTitle = view.findViewById(R.id.editAlbumTitle);
        albumTitle.setText(album.getTitle());

        releaseDate = view.findViewById(R.id.editAlbumReleaseDateEdit);
        releaseDate.setText(album.getReleasedate());
        releaseDate.addTextChangedListener(mDateEntryWatcher);

        EditText albumDescription = view.findViewById(R.id.editAlbumDescriptionEdit);
        albumDescription.setText(album.getDescription());

        return view;
    }



    // Take care from the date format
    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                    isValid = false;
                } else {
                    working+="/";
                    releaseDate.setText(working);
                    releaseDate.setSelection(working.length());
                }
            }
            else if (working.length()==7 && before ==0) {
                String enteredYear = working.substring(3);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) < currentYear) {
                    isValid = false;
                }
            } else if (working.length()!=7) {
                isValid = false;
            }

            if (!isValid) {
                releaseDate.setError("Enter a valid date: MM/YYYY");
            } else {
                releaseDate.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

}
