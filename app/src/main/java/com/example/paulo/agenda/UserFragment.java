package com.example.paulo.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paulo.agenda.database.UserDAO;
import com.example.paulo.agenda.model.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Paulo on 12/03/2015.
 */
public class UserFragment extends Fragment {

    @InjectView(R.id.edit_username)
    public EditText editUsername;

    @InjectView(R.id.edit_password)
    public  EditText editPassword;

    @InjectView(R.id.checkbox_save_log)
    public CheckBox chkSaveLog;

    private UserDAO userDAO;

    private UserFragmentListner listner;

    public  static Fragment newInstance(UserFragmentListner listner){

        UserFragment userFragment = new UserFragment();
        userFragment.setListner(listner);

        return userFragment;
    }

    private void setListner(UserFragmentListner listner) {
        this.listner = listner;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,container,false);
        ButterKnife.inject(this,view);

        if(chkSaveLog.isChecked()){

        }

        return view;
    }


    @OnClick(R.id.btn_logar)
    public void onClickLogar(){
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Usuario ou Senha Invalidos", Toast.LENGTH_SHORT).show();
        }else {
            User user = userDAO.getUser(username,password);
            String msg = "Logado com Sucesso";

            if(user == null){
                msg = "Usuario não Encontrado";
            }else{
                if(chkSaveLog.isChecked()){
                    Helper.saveUserPreference(getActivity(),username,password);
                }

                if(listner != null){
                    listner.onLogin(user);
                }
            }
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_cadastrar)
    public void onClickCadastrar(){
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Erro Dados Não Informados", Toast.LENGTH_SHORT).show();
        }else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNick("UsuarioAgenda");

            Boolean success = userDAO.insert(user);

            String msg = "Usuario Cadastrado Com Sucesso";

            if(!success){
                msg = "Usuario não Encontrado";
            }
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        }
    }

    public interface UserFragmentListner {
        public void onLogin(User user);
    }
}

