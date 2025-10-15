package com.example.lab3_moviles.ui.perfil;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab3_moviles.databinding.FragmentPerfilBinding;
import com.example.lab3_moviles.models.Propietario;
import com.google.android.material.snackbar.Snackbar;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm=new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm.leerPropietario();


        vm.getPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etEmail.setText(propietario.getEmail());
            }
        });

        vm.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
               binding.etEmail.setEnabled(aBoolean);
               binding.etDni.setEnabled(aBoolean);
               binding.etNombre.setEnabled(aBoolean);
               binding.etApellido.setEnabled(aBoolean);

            }
        });
        vm.getTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String nombre = binding.etNombre.getText().toString();
               String apellido = binding.etApellido.getText().toString();
               String dni = binding.etDni.getText().toString();
               String email = binding.etEmail.getText().toString();
               vm.guardar(binding.btnEditar.getText().toString(),nombre,dni,apellido,email);
            }
        });
        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
        return (binding.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}