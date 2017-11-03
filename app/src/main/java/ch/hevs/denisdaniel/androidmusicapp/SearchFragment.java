package ch.hevs.denisdaniel.androidmusicapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class SearchFragment extends Fragment {

    //define paren
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false);
    }
}