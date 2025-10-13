package com.example.lab3_moviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.lab3_moviles.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainActivityViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.etMail.getText().toString();
                String clave = binding.etClave.getText().toString();
                mv.login(mail,clave);
            }
        });
        mv.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

    }
}