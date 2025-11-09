package com.example.lab3_moviles.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentDetallePagoBinding;
import com.example.lab3_moviles.models.Pago;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DetallePagoFragment extends Fragment {

    private DetallePagoViewModel vm;
    private FragmentDetallePagoBinding binding;

    public static DetallePagoFragment newInstance() {
        return new DetallePagoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       vm = new ViewModelProvider(this).get(DetallePagoViewModel.class);
       binding = FragmentDetallePagoBinding.inflate(getLayoutInflater(),container,false);

        vm.getmPago().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos,getContext(),getLayoutInflater());
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                RecyclerView rv = binding.lista;
                rv.setLayoutManager(glm);
                rv.setAdapter(adapter);
            }
        });
        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
        vm.cargarPagos(getArguments());


       return binding.getRoot();
    }



}