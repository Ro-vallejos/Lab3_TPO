package com.example.lab3_moviles.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentDetalleContratoBinding;
import com.example.lab3_moviles.models.Contrato;
import com.google.android.material.snackbar.Snackbar;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;
    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm=new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        limpiarTv();

        vm.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvCodigo.setText(contrato.getId()+"");
                binding.tvFechaInicio.setText(contrato.getFechaInicio());
                binding.tvFechaFinal.setText(contrato.getFechaFin());
                binding.tvMontoMensual.setText(contrato.getMontoMensual() + "");
                binding.tvInquilino.setText(contrato.getInquilino().toString());
                binding.tvInmuebleDireccion.setText(contrato.getInmueble().getDireccion());
                binding.btnPagos.setEnabled(true);
                binding.btnPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato",contrato);
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_detallePago,bundle);
                    }
                });
            }
        });
        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
        vm.getSinContrato().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
                limpiarTv();
            }
        });

        vm.mostrarContrato(getArguments());
        return binding.getRoot();
    }
    private void limpiarTv() {
        binding.tvCodigo.setText("");
        binding.tvFechaInicio.setText("");
        binding.tvFechaFinal.setText("");
        binding.tvMontoMensual.setText("");
        binding.tvInquilino.setText("");
        binding.tvInmuebleDireccion.setText("");
        binding.btnPagos.setEnabled(false);
    }


}