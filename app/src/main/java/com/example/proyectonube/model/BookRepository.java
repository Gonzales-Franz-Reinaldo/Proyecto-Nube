package com.example.proyectonube.model;

import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.IntStream;

// Clase que actúa como repositorio para interactuar con la base de datos Firestore
public class BookRepository {

    private FirebaseFirestore firestore; // Instancia de Firestore para acceder a la base de datos

    // Constructor que inicializa Firestore
    public BookRepository() {
        firestore = FirebaseFirestore.getInstance(); // Obtiene la instancia de Firestore
    }

    // Metodo para obtener todos los libros desde la colección "libros" en Firestore
    public void getAllBooks(OnBooksLoadedListener listener) {
        firestore.collection("libros")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Book> libros = querySnapshot.toObjects(Book.class);
                    // Asignar IDs usando Streams
                    IntStream.range(0, querySnapshot.size())
                            .forEach(i -> libros.get(i).setId(querySnapshot.getDocuments().get(i).getId()));
                    listener.onBooksLoaded(libros);
                })
                .addOnFailureListener(listener::onError);
    }



    // Metodo para agregar un libro a la colección "libros" en Firestore
    public void addBook(Book book, OnCompleteListener<Void> listener) {
        firestore.collection("libros")
                .document()
                .set(book)
                .addOnCompleteListener(listener);
    }

    public void updateBook(Book book, OnCompleteListener<Void> listener) {
        if (book == null || TextUtils.isEmpty(book.getId())) {
            throw new IllegalArgumentException("El libro o su ID no pueden ser nulos");
        }
        firestore.collection("libros")
                .document(book.getId())
                .set(book)
                .addOnCompleteListener(listener);
    }

    public void deleteBook(String bookId, OnCompleteListener<Void> listener) {
        firestore.collection("libros")
                .document(bookId)
                .delete()
                .addOnCompleteListener(listener);
    }

    // Interfaz que define los métodos para manejar los resultados de la carga de libros
    public interface OnBooksLoadedListener {
        // Metodo que se ejecuta cuando los libros se cargan correctamente
        void onBooksLoaded(List<Book> libros);

        // Metodo que se ejecuta cuando ocurre un error al cargar los libros
        void onError(Exception e);
    }

}
