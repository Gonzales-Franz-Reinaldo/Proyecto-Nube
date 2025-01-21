package com.example.proyectonube.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

// Clase que actúa como repositorio para interactuar con la base de datos Firestore
public class BookRepository {

    private FirebaseFirestore firestore; // Instancia de Firestore para acceder a la base de datos

    // Constructor que inicializa Firestore
    public BookRepository() {
        firestore = FirebaseFirestore.getInstance(); // Obtiene la instancia de Firestore
    }

    // Metodo para obtener todos los libros desde la colección "libros" en Firestore
    public void getAllBooks(OnBooksLoadedListener listener) {
        // Accede a la colección llamada "libros"
        firestore.collection("libros")
                .get() // Solicita todos los documentos de la colección
                .addOnSuccessListener(querySnapshot -> {
                    // Convierte los documentos obtenidos en una lista de objetos Book
                    List<Book> libros = querySnapshot.toObjects(Book.class);
                    // Llama al metodo onBooksLoaded del listener y pasa la lista de libros
                    listener.onBooksLoaded(libros);
                })
                .addOnFailureListener(listener::onError);
    }

    // Interfaz que define los métodos para manejar los resultados de la carga de libros
    public interface OnBooksLoadedListener {
        // Metodo que se ejecuta cuando los libros se cargan correctamente
        void onBooksLoaded(List<Book> libros);

        // Metodo que se ejecuta cuando ocurre un error al cargar los libros
        void onError(Exception e);
    }
}
