package com.pennapps.brady.smingle;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ProfilesFragment extends android.support.v4.app.Fragment {


    private FragmentActivity mActivity;
    private ImageAdapter mAdapter;
    private GridView gridView;

    public ProfilesFragment() {
        // Required empty public constructor
    }

    public void setImageAdapter(ImageAdapter adapter) {
//        mAdapter = adapter;
//        gridView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_profiles, container, false);
        gridView = (GridView) v.findViewById(R.id.profileFragment);
        gridView.setAdapter(new ImageAdapter(getActivity()));
//        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(mActivity, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    public void resetAdapter(ArrayList<String[]> profiles) {

    }


}
