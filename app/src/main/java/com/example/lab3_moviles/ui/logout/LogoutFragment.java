package com.example.lab3_moviles.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_moviles.MainActivity;
import com.example.lab3_moviles.R;
import com.example.lab3_moviles.databinding.FragmentLogoutBinding;
import com.example.lab3_moviles.request.ApiClient;

public class LogoutFragment extends Fragment {

    private LogoutViewModel vm;
    private FragmentLogoutBinding binding;
    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(LogoutViewModel.class);
        binding = FragmentLogoutBinding.inflate(inflater,container,false);


        new AlertDialog.Builder(requireContext())
                .setTitle("Cierre de sesión")
                .setMessage("¿Estás seguro de cerrar sesión?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiClient.eliminarToken(requireContext());
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_home);
                    }
                })
                .show();

        return(binding.getRoot());
    }

}