package com.example.proyectonube.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectonube.R;
import com.example.proyectonube.controller.AuthController;

import com.example.proyectonube.utils.InputValidator;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private TextView loginRedirectText;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciar los elementos de la interfaz
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Instanciar el controlador de autenticación
        authController = new AuthController();

        // Acción del botón "Registrar"
        registerButton.setOnClickListener(v -> {
            String name = usernameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

//            // validamos los campos
//            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
//                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // validamos que las contraseñas sean iguales
//            if(!password.equals(confirmPassword)){
//                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
//                return;
//            }
            // Validaciones de los campos
            if (!InputValidator.isValidUsername(name)) {
                Toast.makeText(this, "El nombre de usuario debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!InputValidator.isValidEmail(email)){
                Toast.makeText(this, "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!InputValidator.isValidPassword(password)) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!InputValidator.doPasswordsMatch(password, confirmPassword)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registrar al usuario
            authController.register(email, password, task -> {
                if(task.isSuccessful()){
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(this, "Error al registrarse: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Acción del texto para redirigir al login
        loginRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }
}