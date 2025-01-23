package com.example.proyectonube.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.AuthResult;
import com.example.proyectonube.R;
import com.example.proyectonube.controller.AuthController;
import com.example.proyectonube.view.main.MainActivity;
import com.example.proyectonube.utils.InputValidator;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private LinearLayout googleButton;
    private TextView registerRedirectText;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        googleButton = findViewById(R.id.googleButton);
        registerRedirectText = findViewById(R.id.registerRedirectText);

        authController = new AuthController();


        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            // Validaciones
            if (!InputValidator.isValidEmail(email)) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!InputValidator.isValidPassword(password)) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            authController.login(email, password, task -> {
                if(task.isSuccessful()){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    Log.e("LoginError", task.getException().getMessage());
                }
            });
        });



        googleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, GoogleSignInActivity.class));
        });



        registerRedirectText.setOnClickListener(v -> {
            Log.d("LoginActivity", "Redirigiendo al registro");
            startActivity(new Intent(this, RegisterActivity.class));
//            startActivity(new Intent(this, MainActivity.class));
        });
    }
}