package ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hevs.denisdaniel.androidmusicapp.Albums.Albums.Album;
import ch.hevs.denisdaniel.androidmusicapp.R;


public class AlbumDetailsFragment extends Fragment {
    private static final String ALBUM_ID = "albumId";
    private String albumId;
    private Album album;


    public AlbumDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AlbumDetailsFragment.
     */

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
            albumId = getArguments().getString(ALBUM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_details, container, false);




        return view;
    }


}
