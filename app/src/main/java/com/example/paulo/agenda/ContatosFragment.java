package com.example.paulo.agenda;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.agenda.model.Contato;
import com.example.paulo.agenda.model.User;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo on 12/03/2015.
 */
public class ContatosFragment extends Fragment {

    List<Contato> contatos;
    public static Fragment newInstance(){
        ContatosFragment fragment = new ContatosFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contatos,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContatos();
    }

    private void getContatos(){
        User user  = Helper.getUserPreference(getActivity());
        String url = "http://192.168.0.10:3000/contacts/user/" + user.getId();
        Ion.with(getActivity())
                .load(url)
                .as(new TypeToken<List<Contato>>(){})
                .withResponse()
                .setCallback(new FutureCallback<Response<List<Contato>>>() {
                    @Override
                    public void onCompleted(Exception e, Response<List<Contato>> result) {
                        if(result != null){
                            if(result.getHeaders().code() == 200){
                                if(contatos == null){
                                    contatos = new ArrayList<Contato>(result.getResult());
                                }else{
                                    contatos.addAll(result.getResult());
                                }
                            }
                        }
                    }
                });

    }
}
