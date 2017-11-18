package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hevs.denisdaniel.androidmusicapp.R;

/**
 * Created by Denis Woeffray on 27.10.2017.
 */

public class AddAlbumFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album_add, container, false);

    }


}

