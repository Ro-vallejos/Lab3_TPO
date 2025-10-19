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

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
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
                    mMensaje.setValue("Clave actualizada");
                }else{
                    String msg="";
                    try {
                        if (response.errorBody() != null) {
                            String raw = response.errorBody().string();
                            if (raw != null && !raw.trim().isEmpty()) {
                                raw = raw.trim();
                                if (raw.equalsIgnoreCase("La contraseña actual es incorrecta.")) {
                                    msg = "La contraseña actual es incorrecta.";
                                }else{
                                    msg = "Error al cambiar la clave";
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                    mMensaje.setValue(msg);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mMensaje.setValue("Error en el servidor");
            }
        });
    }

}