package com.example.lab3_moviles.ui.perfil;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.request.ApiClient;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    MutableLiveData<Void> mVolver = new MutableLiveData<>();

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public LiveData<Void> getVolver(){
        return mVolver;
    }

    public void cambiarClave(Editable claveActual, Editable claveNueva, Editable claveNueva2){
        String actual = claveActual.toString();
        String nueva = claveNueva.toString();
        String nuevaRep = claveNueva2.toString();
        if(actual.isEmpty()||nueva.isEmpty()||nuevaRep.isEmpty()){
            mMensaje.setValue("Los campos son obligatorios");
            return;
        }
        if(!nueva.equals(nuevaRep)){
            mMensaje.setValue("La clave nueva y la confirmación no coinciden");
            return;
        }
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(getApplication());
        Call<Void> llamada = api.cambiarClave("Bearer "+ token,actual,nueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Clave actualizada");
                    mVolver.postValue(null);
                }else{
                    String msg="Error al cambiar la contraseña.";
                    try {
                        if (response.errorBody() != null) {
                            String raw = response.errorBody().string();
                            if (raw != null && !raw.trim().isEmpty()) {
                                raw = raw.trim();
                                if (response.code() ==400) {
                                    msg = raw.replace("\"", "");
                                    //msg = "La nueva contraseña no puede ser igual a la actual.";
                                }else if(response.code() == 401){
                                    msg = raw.replace("\"", "");
                                    //msg = "La contraseña actual es incorrecta.";
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                    mMensaje.postValue(msg);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mMensaje.postValue("Error en el servidor");
            }
        });
    }

}