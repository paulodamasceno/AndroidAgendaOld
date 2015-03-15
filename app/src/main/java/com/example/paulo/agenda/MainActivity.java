package com.example.paulo.agenda;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.paulo.agenda.model.User;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements UserFragment.UserFragmentListner {

    public MenuItem provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolbar();

        User user = Helper.getUserPreference(this);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(user == null){
            fragmentTransaction.replace(R.id.content,UserFragment.newInstance(this)).commit();
        }else{
            fragmentTransaction.replace(R.id.content,ContatosFragment.newInstance()).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        provider =  menu.findItem(R.id.action_import);

        provider.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_import){
            Intent intent = new Intent(this,ProviderActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogin(User user) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content, ContatosFragment.newInstance()).commit();
        provider.setVisible(true);
    }
}
