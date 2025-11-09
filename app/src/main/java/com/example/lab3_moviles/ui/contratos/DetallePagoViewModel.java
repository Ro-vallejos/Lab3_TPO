package com.example.lab3_moviles.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.models.Contrato;
import com.example.lab3_moviles.models.Pago;
import com.example.lab3_moviles.request.ApiClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagoViewModel extends AndroidViewModel {

    MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public MutableLiveData<List<Pago>> mPago = new MutableLiveData<>();

    public DetallePagoViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<List<Pago>> getmPago() {
        return mPago;
    }
    public MutableLiveData<String> getMensaje() {
        return mMensaje;
    }
    public void cargarPagos(Bundle b){
        Contrato contrato = (Contrato) b.getSerializable("contrato");
        if(contrato == null){
            mMensaje.setValue("No se encontraron pagos del contrato.");
        }else{
            String token = ApiClient.leerToken(getApplication());
            Call<List<Pago>> llamada = ApiClient.getApiInmobiliaria().getPagos("Bearer "+ token, contrato.getId());
            llamada.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    List<Pago> con = response.body();

                    if(response.isSuccessful()){
                        List<Pago> pagos = response.body();
                        if(pagos!=null){
                            for(Pago p : pagos){
                                String fechaFormat = formatearFecha(p.getFechaPago());
                                p.setFechaPago(fechaFormat);
                            }
                            mPago.setValue(pagos);
                        }
                    }else {
                        mMensaje.setValue("Error al obtener pagos.");
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable t) {
                    mMensaje.setValue("Error en el servidor.");
                }
            });
        }
    }
    public static String formatearFecha(String fecha) {
        try {
            LocalDate d = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return fecha;
        }
    }

}