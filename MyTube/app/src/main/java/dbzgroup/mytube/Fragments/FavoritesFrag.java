package dbzgroup.mytube.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dbzgroup.mytube.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFrag extends Fragment {
    private View v;

    public FavoritesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorites, container, false);
        // Inflate the layout for this fragment
        return v;
    }

}
