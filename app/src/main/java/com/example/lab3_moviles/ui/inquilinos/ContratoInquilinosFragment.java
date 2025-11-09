package com.example.lab3_moviles.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentContratoBinding;
import com.example.lab3_moviles.models.Inmueble;
import com.example.lab3_moviles.ui.contratos.ContratoAdapter;
import com.example.lab3_moviles.ui.contratos.ContratoViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ContratoInquilinosFragment extends Fragment {

    private ContratoViewModel vm;
    private FragmentContratoBinding binding;

    public static ContratoInquilinosFragment newInstance() {
        return new ContratoInquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater,container,false);

        vm.obtenerInmuebles();
        vm.getInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ContratoAdapter card = new ContratoAdapter(inmuebles,getLayoutInflater(),getContext(), new ContratoAdapter.verDetalleClicListener() {
                    @Override
                    public void verListener(Inmueble inmueble) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("inmueble",inmueble);
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_detalleInquilino,bundle);
                    }
                });

                GridLayoutManager glm = new GridLayoutManager(getContext(),2);
                RecyclerView rv = binding.lista;
                rv.setLayoutManager(glm);
                rv.setAdapter(card);
            }
        });
        vm.getMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }




}