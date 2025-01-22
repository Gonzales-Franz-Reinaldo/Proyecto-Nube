package com.example.proyectonube.view.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.proyectonube.R;
import com.example.proyectonube.controller.AuthController;
import com.example.proyectonube.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

public class ProfileFragment extends Fragment {
    ImageView profileImage;
    private TextView userName, userEmail;
    private Button logoutButton, deleteAccountButton;
    private AuthController authController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // inicializar vistas
        profileImage = view.findViewById(R.id.profileImage);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        logoutButton = view.findViewById(R.id.logoutButton);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);

        // inicializar controlador de autenticación
        authController = new AuthController();
        setupProfile();

        // Configurar el botón de cerrar sesión
        logoutButton.setOnClickListener(v -> {
            authController.logout();
            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
        });

        // Configurar el botón de eliminar cuenta
        deleteAccountButton.setOnClickListener(v -> {
            showDeleteAccountDialog();
        });

        return view;
    }

    private void setupProfile(){
        FirebaseUser user = authController.getCurrentUser();

        if(user != null){
            // Configurar nombre
            String nombre = user.getDisplayName() != null ? user.getDisplayName() : "Usuario";
            userName.setText(nombre);
            // Configurar correo
            String email = user.getEmail() != null ? user.getEmail() : "No Disponible";
            userEmail.setText(email);

            // Configurar imagen de perfil
            String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
            if(photoUrl != null){
                // Cargar imagen
                Glide.with(this).load(photoUrl).circleCrop().into(profileImage);
            } else {
                // Cargar imagen por defecto
                profileImage.setImageResource(R.drawable.default_profile);
            }
        }
    }

    private void showDeleteAccountDialog(){
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que deseas eliminar tu cuenta?")
                .setPositiveButton("Eliminar", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteAccount(){
        FirebaseUser user = authController.getCurrentUser();
        if(user != null){
            user.delete().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
