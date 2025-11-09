package com.example.lab3_moviles.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSwitch = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSwitchVisibility = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAlquiladoVisibility = new MutableLiveData<>();

    public LiveData<Boolean> getmSwitch(){return mSwitch;}
    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }

    public LiveData<Integer> getSwitchVisibility(){ return mSwitchVisibility; }
    public LiveData<Integer> getAlquiladoVisibility(){ return mAlquiladoVisibility; }
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtenerInmueble (Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");
        if (inmueble != null) {
            mInmueble.setValue(inmueble);
            mSwitch.setValue(inmueble.getEstado() == 1);

            if (inmueble.getEstado() == 3) {
                mSwitchVisibility.setValue(8);
                mAlquiladoVisibility.setValue(0);
            } else {
                mSwitchVisibility.setValue(0);
                mAlquiladoVisibility.setValue(8);
            }
        }
    }


    public void actualizarEstado ( Boolean disponible){
        Inmueble inmueble = new Inmueble();
        if(disponible)
            inmueble.setEstado(1);
        else
            inmueble.setEstado(2);
        inmueble.setId(mInmueble.getValue().getId());
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