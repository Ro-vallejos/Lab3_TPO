package com.example.lab3_moviles;

import static java.lang.Math.sqrt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab3_moviles.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private Float acceleration = 0f;
    private Float anteriorAcceleration = 0f;
    private Float actualAcceleration = 0f;
    private SensorManager manager;
    private List<Sensor> sensores;
    private ManejaSensores maneja;
    private final float SHAKE_THRESHOLD = 52f;
    private final float SHAKE_RESET_THRESHOLD = 0.001f;
    private boolean shakeInProgress = false;
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
    private class ManejaSensores implements SensorEventListener{

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float ejeX = event.values[0];
            float ejeY = event.values[1];
            float ejeZ = event.values[2];
            anteriorAcceleration = actualAcceleration;

            actualAcceleration = (float) sqrt((ejeX * ejeX + ejeY * ejeY + ejeZ * ejeZ));
            float delta = actualAcceleration - anteriorAcceleration;
            acceleration = acceleration * 0.9f + delta;

            if (!shakeInProgress && acceleration > 12 ) {
                shakeInProgress = true;
                llamarInmobiliaria();
            }else if (shakeInProgress && acceleration < SHAKE_RESET_THRESHOLD){
                shakeInProgress = false;
            }
        }
    }
    public void activarLecturas(){
        manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        maneja = new ManejaSensores();
        manager.registerListener(maneja, sensores.get(0), SensorManager.SENSOR_DELAY_GAME);
    }
    public void desactivarLecturas(){
        if (!sensores.isEmpty())
            manager.unregisterListener(maneja);
    }
    private void llamarInmobiliaria() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:266444444"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
}