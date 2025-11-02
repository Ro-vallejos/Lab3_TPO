package com.example.lab3_moviles.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab3_moviles.R;
import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;
import com.google.android.material.navigation.NavigationBarItemView;

import org.w3c.dom.Text;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
    private List<Inmueble> lista;
    private Context context;
    public InmuebleAdapter(List<Inmueble> lista, Context context){
        this.lista=lista;
        this.context=context;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card,parent,false);
       return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble inmuebleActual = lista.get(position);
        holder.tvValor.setText(String.valueOf(inmuebleActual.getValor()));
        holder.tvDireccion.setText(inmuebleActual.getDireccion());
        Glide.with(context)
                .load(ApiClient.URLBASE + inmuebleActual.getImagen())
                .placeholder(R.drawable.inmuebles)
                .error("null")
                .into(holder.imgInmueble);
        holder.idCard.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmuebleActual);
            Navigation.findNavController((Activity)v.getContext(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_detalleInmueble,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvValor;
        private ImageView imgInmueble;
        private CardView idCard;
        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion=itemView.findViewById(R.id.tvDireccion);
            tvValor=itemView.findViewById(R.id.tvValor);
            imgInmueble=itemView.findViewById(R.id.imgInmueble);
            idCard=itemView.findViewById(R.id.idCard);
        }
    }
}
