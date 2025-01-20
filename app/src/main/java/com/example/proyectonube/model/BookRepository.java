package com.example.proyectonube.model;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BookRepository {
    private FirebaseFirestore firestore;

    public BookRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void getAllBooks(OnBooksLoadedListener listener) {
        firestore.collection("libros")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Book> libros = querySnapshot.toObjects(Book.class);
                    listener.onBooksLoaded(libros);
                })
                .addOnFailureListener(listener::onError);
    }

    public interface OnBooksLoadedListener {
        void onBooksLoaded(List<Book> libros);
        void onError(Exception e);
    }
}
