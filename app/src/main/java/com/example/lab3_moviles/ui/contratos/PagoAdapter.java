package com.example.lab3_moviles.ui.contratos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.models.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder>{
    private List<Pago> lista;
    private Context context;
    private LayoutInflater li ;
    public PagoAdapter(List<Pago> lista, Context context, LayoutInflater li){
        this.lista=lista;
        this.context=context;
        this.li = li;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.pago_card,parent,false);
        return new PagoAdapter.PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {

        Pago pagoActual = lista.get(position);
        Log.d("carajo", "PAGOS: " + pagoActual.toString());
        holder.tvFechaPago.setText(pagoActual.getFechaPago());
        holder.tvImporte.setText("$" + (pagoActual.getImporte()));
        holder.tvCodigoPago.setText(pagoActual.getId() +"");
        holder.tvCodigoContrato.setText(pagoActual.getIdContrato() + "");
        holder.tvNumeroPago.setText(pagoActual.getNroPago() + "");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class PagoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNumeroPago, tvCodigoPago,tvCodigoContrato, tvImporte, tvFechaPago;
        private CardView idCard;
        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroPago=itemView.findViewById(R.id.tvNumeroPago);
            tvCodigoPago=itemView.findViewById(R.id.tvCodigoPago);
            tvCodigoContrato=itemView.findViewById(R.id.tvCodigoContrato);
            tvImporte=itemView.findViewById(R.id.tvImporte);
            tvFechaPago=itemView.findViewById(R.id.tvFechaPago);
            idCard=itemView.findViewById(R.id.idCard);
        }
    }
}
