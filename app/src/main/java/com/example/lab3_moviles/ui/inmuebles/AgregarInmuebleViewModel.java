package com.example.lab3_moviles.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();
    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Uri> getUriMutable() {
        return uriMutableLiveData;
    }
    public LiveData<String> getMensaje(){
        return mMensaje;
    }

    public void guardarInmueble (String direccion, String precio, String tipo, String uso, String cantAmbientes, String lat, String longi, String sup){
        double valor, latitud, longitud;
        int ambientes, superficie;
        boolean disponible;
        try{
            if(direccion.isEmpty()||tipo.isEmpty()||uso.isEmpty()||cantAmbientes.isEmpty()||sup.isEmpty()||precio.isEmpty()||lat.isEmpty()||longi.isEmpty()){
                mMensaje.setValue("Todos los campos son obligatorios.");
                return;
            }
             valor= Double.parseDouble(precio);
             ambientes= Integer.parseInt(cantAmbientes);
             superficie= Integer.parseInt(sup);
             latitud = Double.parseDouble(lat);
             longitud = Double.parseDouble(longi);

        } catch (NumberFormatException e) {
            mMensaje.setValue("Debe ingresar valores numéricos en los campos 'Valor', 'Ambientes','Superficie','Latitud', 'Longitud' ");
            return;
        }
        if(uriMutableLiveData.getValue()==null){
            mMensaje.setValue("Debe seleccionar una foto.");
            return;
        }

            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setEstado(1);
            inmueble.setAmbientes(ambientes);
            inmueble.setLatitud(latitud);
            inmueble.setLongitud(longitud);
            inmueble.setTipo(tipo);
            inmueble.setSuperficie(superficie);
            inmueble.setUso(uso);
            inmueble.setPrecio(valor);


            byte[] imagen = transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);

            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);


        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);


        ApiClient.InmoService inmoServicio = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = inmoServicio.cargarInmueble("Bearer "+token, imagenPart,inmuebleBody);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    mMensaje.postValue("Inmueble cargado exitosamente.");
                }else {
                    mMensaje.postValue("Error al cargar el inmueble.");
                    try{
                        Log.d("carajo", "Fallo: " + response.errorBody().string());
                        Log.d("carajo", "Fallo: " + inmueble.toString());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                mMensaje.postValue("Error en el servidor.");
            }
        });
    }
    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }
    private byte[] transformarImagen(){
        try{
            Uri uri= uriMutableLiveData.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            mMensaje.setValue("No ha seleccionado una foto.");
            return new byte[]{};
        }
    }


}