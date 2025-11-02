package com.example.lab3_moviles.ui.inmuebles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        obtenerInmuebles();
    }

    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public LiveData<List<Inmueble>> getMInmueble(){
        return mInmueble;
    }

    public void obtenerInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.getInmuebles("Bearer " +token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    mInmueble.postValue(response.body());
                }else{
                    mMensaje.setValue("No hay inmuebles disponibles.");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                mMensaje.setValue("Error en el servidor.");
            }
        });
    }
}