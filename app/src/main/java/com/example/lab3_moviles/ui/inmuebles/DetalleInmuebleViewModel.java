package com.example.lab3_moviles.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtenerInmueble (Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");
        if(inmueble!=null){
            mInmueble.setValue(inmueble);
        }
    }

    public void actualizarEstado ( Boolean disponible){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(mInmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = ApiClient.getApiInmobiliaria().updateInmueble("Bearer "+token,inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Inmueble actualizado con éxito.");
                }else{
                    mMensaje.postValue("Error al actualizar el inmueble.");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mMensaje.postValue("Error en el servidor.");
            }
        });
    }

}