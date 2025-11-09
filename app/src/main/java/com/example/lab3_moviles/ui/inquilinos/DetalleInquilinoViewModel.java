package com.example.lab3_moviles.ui.inquilinos;



import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.models.Contrato;
import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.models.Inquilino;
import com.example.lab3_moviles.models.Pago;
import com.example.lab3_moviles.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {
    MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensaje() {
        return mMensaje;
    }

    public MutableLiveData<Inquilino> getmInquilino() {
        return mInquilino;
    }
    public void cargarInquilino(Bundle b){
        Inmueble inm = (Inmueble) b.getSerializable("inmueble");
        if(inm == null){
            mMensaje.setValue("No se encontró el inquilino.");
        }else{
            String token = ApiClient.leerToken(getApplication());
            Call<Contrato> llamada = ApiClient.getApiInmobiliaria().cargarContrato("Bearer "+token,inm.getId());
            llamada.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if(response.isSuccessful()){
                        Contrato contrato = response.body();
                        Inquilino inquilino = contrato.getInquilino();
                        mInquilino.setValue(inquilino);
                    }else if(response.code() == 404){
                        mMensaje.setValue("Inmueble sin inquilino.");
                    }else {
                        mMensaje.setValue("Error al obtener inquilino");
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    mMensaje.setValue("Error en el servidor.");
                }
            });
        }
    }
}