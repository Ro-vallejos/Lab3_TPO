package com.example.lab3_moviles.ui.perfil;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_moviles.models.Propietario;
import com.example.lab3_moviles.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String>mMensaje = new MutableLiveData<>();
    private MutableLiveData<Propietario>mPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mTextoBoton = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getTextoBoton(){
        return mTextoBoton;
    }
    public LiveData<Boolean> getEstado(){
        return mEstado;
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }
    public LiveData<Propietario> getPropietario(){
        return mPropietario;
    }

    public void guardar (String textoBoton, String nombre, String dni, String apellido, String email, String telefono){
        if(textoBoton.equals("Editar")){
            mEstado.setValue(true);
            mTextoBoton.setValue("Guardar");
        }else{
            if(nombre.isEmpty()||apellido.isEmpty()||email.isEmpty()||dni.isEmpty()||telefono.isEmpty()){
                mMensaje.setValue("Los campos son obligatorios");
                return;
            }
            if (!TextUtils.isDigitsOnly(dni)) {
                mMensaje.setValue("El campo dni debe contener solo números");
                return;
            }
            if (!TextUtils.isDigitsOnly(telefono)) {
                mMensaje.setValue("El campo teléfono debe contener solo números");
                return;
            }

            Propietario p = new Propietario();
            p.setIdPropietario(mPropietario.getValue().getIdPropietario());
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setDni(dni);
            p.setEmail(email);
            p.setTelefono(telefono);
            p.setClave(null);

            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().updatePropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        mMensaje.postValue("Se actualizaron los cambios");
                    }else{
                        mMensaje.postValue("Error al actualizar");

                    }
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    mMensaje.postValue("Error en el servidor");

                }
            });
            mEstado.setValue(false);
            mTextoBoton.setValue("Editar");
        }
    }
    public void leerPropietario (){
       String token = ApiClient.leerToken(getApplication());
       Call<Propietario> llamada = ApiClient.getApiInmobiliaria().getPropietario("Bearer "+ token);
       llamada.enqueue(new Callback<Propietario>() {
           @Override
           public void onResponse(Call<Propietario> call, Response<Propietario> response) {
               if(response.isSuccessful()){
                   mPropietario.postValue(response.body());
               }else{
                   mMensaje.postValue("No se pudo obtener el propietario");
               }
           }

           @Override
           public void onFailure(Call<Propietario> call, Throwable t) {
               mMensaje.postValue("Error de Servidor");
           }
       });
    }

}