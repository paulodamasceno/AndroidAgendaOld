package com.example.paulo.agenda;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Paulo on 12/03/2015.
 */
public class ContatosFragment extends Fragment {

    public static Fragment newInstance(){
        ContatosFragment fragment = new ContatosFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contatos,container,false);

        return view;
    }
}
