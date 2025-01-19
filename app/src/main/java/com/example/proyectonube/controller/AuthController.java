package com.example.proyectonube.controller;

import android.app.Activity;
import android.media.MediaPlayer;

import com.example.proyectonube.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthController {
    private FirebaseAuth auth;

    // Para la autenticación con Google
    private GoogleSignInClient googleSignInClient;
    public AuthController(){
        this.auth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> listener){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void register(String email, String password, OnCompleteListener<AuthResult> listener){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }


    // CONFIGURACION DE GOOGLE
    public GoogleSignInClient configureGoogleSignIn(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id)) // El ID de cliente de Firebase
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
        return googleSignInClient;
    }

    // Manejar credenciales de Google en Firebase
    public void signInWithGoogle(GoogleSignInAccount account, OnCompleteListener<AuthResult> listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(listener);
    }


    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public void logout(){
        auth.signOut();
    }
}
