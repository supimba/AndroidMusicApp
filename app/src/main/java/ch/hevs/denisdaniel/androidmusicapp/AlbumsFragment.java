package ch.hevs.denisdaniel.androidmusicapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class AlbumsFragment extends Fragment {

    public AlbumsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_albums_list, container, false);

        ArrayList<Album> data = new ArrayList<>();
        Album a1 = new Album("Somewhere in Time");
        Album a2 = new Album("Evil Empire");
        Album a3 = new Album("Kill Em All");

        data.add(a1);
        data.add(a2);
        data.add(a3);

        BindDictionary<Album> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.textviewTitle, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return album.getTitle();
            }
        });
        dictionary.addStringField(R.id.textviewRate, new StringExtractor<Album>() {
            @Override
            public String getStringValue(Album album, int position) {
                return ""+ album.getRating();
            }
        });

        FunDapter adapter = new FunDapter(AlbumsFragment.this.getActivity(), data, R.layout.fragment_albums, dictionary) ;

        ListView lvData = (ListView) view.findViewById(R.id.lvData);
        lvData.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;

    }
}
