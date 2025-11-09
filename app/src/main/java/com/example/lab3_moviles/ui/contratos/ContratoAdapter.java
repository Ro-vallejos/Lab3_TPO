package com.example.lab3_moviles.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.List;
public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder>{

    private List<Inmueble> lista;
    private Context context;
    private ContratoAdapter.verDetalleClicListener listener;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> lista,LayoutInflater inflater, Context context, ContratoAdapter.verDetalleClicListener listener){
        this.lista=lista;
        this.inflater=inflater;
        this.context=context;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.contrato_card,parent,false);
        return new ContratoAdapter.ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble inmuebleActual = lista.get(position);
        holder.tvDireccion.setText(inmuebleActual.getDireccion());
        Glide.with(context)
                .load(ApiClient.URLBASE + inmuebleActual.getImagen())
                .placeholder(R.drawable.inmuebles)
                .error("null")
                .into(holder.imgInmueble);
        holder.btnDetalles.setOnClickListener(v->{listener.verListener(inmuebleActual);});
//
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class ContratoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion;
        private Button btnDetalles;
        private ImageView imgInmueble;
        private CardView idCard;
        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion=itemView.findViewById(R.id.tvDireccion);
            btnDetalles=itemView.findViewById(R.id.btnDetalles);
            imgInmueble=itemView.findViewById(R.id.imgInmueble);
            idCard=itemView.findViewById(R.id.idCard);
        }
    }
    public interface verDetalleClicListener{
        void verListener(Inmueble inmueble);
    }
}
