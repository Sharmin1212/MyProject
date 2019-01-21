package org.ieselcaminas.pro.myfirstproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentProfile extends Fragment {

    public FragmentProfile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_profile, container, false);


        return thisView;
    }


}


