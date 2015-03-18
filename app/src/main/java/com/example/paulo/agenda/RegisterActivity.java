package com.example.paulo.agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.paulo.agenda.model.Contato;
import com.example.paulo.agenda.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.foto_contato)
    ImageView fotoContato;

    @InjectView(R.id.nome_contato)
    EditText nomeContato;

    @InjectView(R.id.email_contato)
    EditText emailContato;

    @InjectView(R.id.phone_contato)
    EditText phoneContato;

    @InjectView(R.id.cellphone_contato)
    EditText cellPhoneContato;

    @InjectView(R.id.address_contato)
    EditText addressContato;

    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initToolbar();

        if(getIntent().hasExtra("contato")){
            contato = (Contato) getIntent().getSerializableExtra("contato");

            nomeContato.setText(contato.getName());

            if( TextUtils.isEmpty(contato.getEmail()) && contato.getEmails().size() > 0){
                emailContato.setText(contato.getEmails().get(0));
            }
            else{
                emailContato.setText(contato.getEmail());
            }

            phoneContato.setText(contato.getPhone());
            cellPhoneContato.setText(contato.getCellphone());
            addressContato.setText(contato.getCellphone());

            if(!TextUtils.isEmpty(contato.getPhoto())){
                Picasso.with(this).load(contato.getPhoto()).into(fotoContato);
            }
        }else{
            contato = new Contato();
        }

    }

    @OnClick(R.id.bt_salvarContato)
    public void onClickSave(View view){

        contato.setName(nomeContato.getText().toString());
        contato.setEmail(emailContato.getText().toString());
        contato.setPhone(phoneContato.getText().toString());
        contato.setAddress(addressContato.getText().toString());
        contato.setCellphone(cellPhoneContato.getText().toString());

        User user = Helper.getUserPreference(this);
        contato.setUserId(user.getId());

        saveContato(contato);

    }

    @OnClick(R.id.foto_contato)
    public void onClicFotoContato(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == RESULT_OK){
            if(data.getData() != null){
                contato.setPhoto(data.getData().toString());
                Picasso.with(this).load(data.getData()).into(fotoContato);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveContato(Contato contato){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Ion.with(this)
                .load("http://192.168.0.10:3000/contacts")
                .progressDialog(progressDialog)
                .setJsonPojoBody(contato)
                .asJsonObject()
                .withResponse()
                .setCallback( new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if(result != null){
                            if(result.getHeaders().code() == 200){
                                Gson gson = new Gson();
                                Contato contato1 = gson.fromJson(result.getResult(),Contato.class);
                                Toast.makeText(RegisterActivity.this,"Contato Cadastrado Com Sucesso",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"Não foi possivel cadastrar o contato",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void  editContato(Contato contato){

    }
}
