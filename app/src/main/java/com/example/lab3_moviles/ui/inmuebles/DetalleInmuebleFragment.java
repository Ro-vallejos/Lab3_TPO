package com.example.lab3_moviles.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentDetalleInmuebleBinding;
import com.example.lab3_moviles.request.ApiClient;
import com.google.android.material.snackbar.Snackbar;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel vm;
    private FragmentDetalleInmuebleBinding binding;
    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm= new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding= FragmentDetalleInmuebleBinding.inflate(inflater,container,false);

        vm.getmInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvCodigo.setText(inmueble.getIdInmueble() +"");
            binding.tvDireccion.setText(inmueble.getDireccion());
            binding.tvAmbientes.setText(inmueble.getAmbientes()+"");
            binding.tvTipo.setText(inmueble.getTipo());
            binding.tvLatitud.setText(inmueble.getLatitud()+"");
            binding.tvLongitud.setText(inmueble.getLongitud()+"");
            binding.tvUso.setText(inmueble.getUso());
            binding.tvPrecio.setText(inmueble.getValor()+"");
            Glide.with(this)
                    .load(ApiClient.URLBASE + inmueble.getImagen())
                    .placeholder(R.drawable.inmuebles)
                    .error("null")
                    .into(binding.imgInmueble);
            binding.switchDisponible.setChecked(inmueble.isDisponible());
        });
        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.switchDisponible.setOnClickListener(v->{
            vm.actualizarEstado(binding.switchDisponible.isChecked());
        });
        vm.obtenerInmueble(getArguments());


        return binding.getRoot();
    }


}