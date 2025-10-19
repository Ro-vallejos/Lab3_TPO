package com.example.lab3_moviles.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lab3_moviles.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {


    //url base
    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    //metodos para guardar y leer token
    public static void guardarToken(Context context, String token){
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.apply();
    }
    //retrofit
    public static InmoService getApiInmobiliaria(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoService.class);
    }
    public static String leerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token.xml",Context.MODE_PRIVATE);
        return sp.getString("token",null);
    }
    public static void eliminarToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        editor.commit();
    }
    //interface
    public interface InmoService{
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String mail, @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario>getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario>updatePropietario(@Header("Authorization")String token, @Body Propietario p);
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarClave(@Header("Authorization")String token, @Field("currentPassword") String claveActual, @Field("newPassword") String claveNueva);
    }
}
