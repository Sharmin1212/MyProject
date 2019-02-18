package org.ieselcaminas.pro.myfirstproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class FragmentPlans extends android.support.v4.app.Fragment {

    Button buttonBuyDelivery;
    Button buttonBuyConsumer;
    Button buttonBuyNoAds;

    public FragmentPlans() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_plans, container, false);

        buttonBuyDelivery = thisView.findViewById(R.id.buttonBuyDelivery);
        buttonBuyConsumer = thisView.findViewById(R.id.buttonBuyConsumer);
        buttonBuyNoAds = thisView.findViewById(R.id.buttonBuyNoAds);

        buttonBuyDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Singleton.sharedInstance().isAuthenticated()) {
                    Toast.makeText(thisView.getContext(), getString(R.string.logInFirst), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBuyConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Singleton.sharedInstance().isAuthenticated()) {
                    Toast.makeText(thisView.getContext(), getString(R.string.logInFirst), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBuyNoAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Singleton.sharedInstance().isAuthenticated()) {
                    Toast.makeText(thisView.getContext(), getString(R.string.logInFirst), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return thisView;
    }

}
