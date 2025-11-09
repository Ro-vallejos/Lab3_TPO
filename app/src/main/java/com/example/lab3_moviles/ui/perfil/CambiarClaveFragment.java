package com.example.lab3_moviles.ui.perfil;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentCambiarClaveBinding;
import com.google.android.material.snackbar.Snackbar;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel vm;
    private FragmentCambiarClaveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CambiarClaveViewModel.class);
        binding=FragmentCambiarClaveBinding.inflate(inflater,container,false);

        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               vm.cambiarClave(binding.etClaveActual.getText(),binding.etClaveNueva.getText(),binding.etClaveNueva2.getText());
            }
        });
        vm.getVolver().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(Void unused) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        return (binding.getRoot());
    }



}