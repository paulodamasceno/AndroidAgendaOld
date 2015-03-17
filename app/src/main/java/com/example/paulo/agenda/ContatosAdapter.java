package com.example.paulo.agenda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paulo.agenda.model.Contato;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Paulo on 16/03/2015.
 */
public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ViewHolder> {

    List<Contato> contatos;
   Context context;
    View.OnClickListener onClickImport;

    public ContatosAdapter(List<Contato> contatos, Context context, View.OnClickListener onClickImport) {
        this.contatos = contatos;
        this.context = context;
        this.onClickImport = onClickImport;
    }

    @Override
    public ContatosAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contatos,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.btImport.setOnClickListener(onClickImport);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContatosAdapter.ViewHolder viewHolder, int i) {

        Contato contato = contatos.get(i);

        if(!TextUtils.isEmpty(contato.getPhoto())) {
            Picasso.with(context).load(contato.getPhoto()).into(viewHolder.imageContato);
        }



        viewHolder.contatoNome.setText(contato.getName());

        if (TextUtils.isEmpty(contato.getEmail()) && contato.getEmails().size() > 0){
            viewHolder.contatoEmail.setText(contato.getEmails().get(0));
        }else{
            viewHolder.contatoEmail.setText(contato.getEmail());
        }

        viewHolder.btImport.setTag(i);

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.img_contato)
        ImageView imageContato;

        @InjectView(R.id.contato_nome)
        TextView contatoNome;

        @InjectView(R.id.bt_import)
        Button btImport;

        @InjectView(R.id.contato_email)
        TextView contatoEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }
    }
}
