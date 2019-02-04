package org.ieselcaminas.pro.myfirstproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentProfile extends Fragment {
    TextView textViewUsername;


    public FragmentProfile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_profile, container, false);


        textViewUsername = thisView.findViewById(R.id.textViewUsername);
        textViewUsername.setText(Singleton.sharedInstance().getmAuth().getCurrentUser().getDisplayName());

        return thisView;
    }


}


