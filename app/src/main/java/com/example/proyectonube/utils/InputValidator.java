package com.example.proyectonube.utils;

import android.util.Patterns;

public class InputValidator {

    // Validar formato del correo electrónico
    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validar que la contraseña cumpla con los requisitos mínimos
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6; // Mínimo 6 caracteres
    }

    // Validar que el nombre de usuario tenga una longitud mínima
    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3; // Mínimo 3 caracteres
    }

    // Verificar que no existan campos vacíos
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validar que dos contraseñas coincidan
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
