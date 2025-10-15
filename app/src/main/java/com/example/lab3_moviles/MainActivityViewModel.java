package com.example.lab3_moviles;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab3_moviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public void login(String mail, String clave) {


        if(clave.isEmpty()||mail.isEmpty()){
            mMensaje.setValue("Todos los campos son obligatorios");
            return;
        }
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(mail, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token= response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }
                else
                    mMensaje.postValue("Usuario y/o contraseña Incorrecta; reintente");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de Servidor");
            }
        });

    }
}