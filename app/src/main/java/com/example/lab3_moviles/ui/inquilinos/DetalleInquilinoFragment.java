package com.example.lab3_moviles.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentDetalleInquilinoBinding;
import com.example.lab3_moviles.models.Inquilino;
import com.google.android.material.snackbar.Snackbar;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(getLayoutInflater(),container,false);

        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
        vm.getmInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvDni.setText(inquilino.getDni());
                binding.tvEmail.setText(inquilino.getEmail());
                binding.tvNombre.setText(inquilino.toString());
                binding.tvTelefono.setText(inquilino.getTelefono());
            }
        });

        return binding.getRoot();
    }


}