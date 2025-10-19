package com.example.lab3_moviles.ui.home;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class HomeViewModel extends AndroidViewModel {
    private FusedLocationProviderClient fused;
    private Context context;
    private MutableLiveData<MapaActual> mMapaActual;
    private MutableLiveData<Location> mLocation;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
        fused= LocationServices.getFusedLocationProviderClient(context);
    }

    public LiveData<Location> getMlocation(){
        if(mLocation==null){
            mLocation=new MutableLiveData<>();
        }
        return mLocation;
    }
    public LiveData<MapaActual> getMapaActual(){
        if(mMapaActual==null){
            mMapaActual= new MutableLiveData<>();
        }
        return mMapaActual;
    }
    public void cargarMapa(Location ubicacion){
        MapaActual mapaActual = new MapaActual(ubicacion);
        mMapaActual.setValue(mapaActual);
    }
    public void obtenerUbicacion(){
        Location ubicacion = new Location("ubicacion_manual");
        ubicacion.setLatitude(-33.148411904954);
        ubicacion.setLongitude(-66.307301027387);
        cargarMapa(ubicacion);
    }

    public class MapaActual implements OnMapReadyCallback {

        LatLng ubicacion;

        public MapaActual(Location ubicacion){
            this.ubicacion = new LatLng(ubicacion.getLatitude(),ubicacion.getLongitude());
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(ubicacion);
            marcador.title("Inmobiliaria La Punta");

            googleMap.addMarker(marcador);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(ubicacion)
                    .zoom(17)
                    .bearing(45)
                    .tilt(15).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);

        }
    }
}