package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Calendar;

import ch.hevs.denisdaniel.androidmusicapp.AppDatabase;
import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class AddAlbumFragment extends Fragment {
    private AppDatabase db;
    private EditText releaseDate;



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_add, container, false);

        final int[] nbTrack = {0};

        ImageButton addTrack = view.findViewById(R.id.addTrack);
        Button addAlbum = view.findViewById(R.id.buttonAddAlbum);

        final LinearLayout mainLayout = view.findViewById(R.id.album_tracks);
        addTrack.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                View newLL = inflater.inflate(R.layout.track_add, container, false);
                newLL.setId(nbTrack[0]);
                nbTrack[0]++;
                mainLayout.addView(newLL);
            }
        });

        releaseDate = view.findViewById(R.id.albumReleaseDate);
        releaseDate.addTextChangedListener(mDateEntryWatcher);


        return view;
    }



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

