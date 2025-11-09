package com.example.lab3_moviles.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab3_moviles.models.Contrato;
import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    public MutableLiveData<String> mSinContrato = new MutableLiveData<>();


    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public LiveData<Contrato> getContrato(){
        return mContrato;
    }
    public LiveData<String> getSinContrato(){return mSinContrato;}
    public void mostrarContrato(Bundle b){
        Inmueble inmuebleActual = (Inmueble) b.getSerializable("inmueble");
        if(inmuebleActual==null)
        {
            mSinContrato.setValue("Error al mostrar el inmueble");
        }else{
            String token = ApiClient.leerToken(getApplication());
            Call<Contrato> llamada = ApiClient.getApiInmobiliaria().cargarContrato("Bearer "+ token,inmuebleActual.getId());
            llamada.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if(response.isSuccessful()){
                        Contrato contrato = response.body();
                        if(contrato!=null){
                            String fechaInit= formatearFecha(contrato.getFechaInicio());
                            String fechaFin = formatearFecha(contrato.getFechaFin());
                            contrato.setFechaInicio(fechaInit);
                            contrato.setFechaFin(fechaFin);
                            mContrato.postValue(contrato);
                        }else{
                            mSinContrato.postValue("El inmueble no tiene contrato vigente.");
                        }
                    }else{
                        mSinContrato.postValue("Error al obtener el inmueble.");
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    mMensaje.postValue("Error en el servidor");
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