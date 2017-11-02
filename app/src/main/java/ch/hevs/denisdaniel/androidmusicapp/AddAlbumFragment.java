package ch.hevs.denisdaniel.androidmusicapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class AddAlbumFragment extends Fragment {
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_album, container, false);
    }



}

