package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;

import javax.annotation.Nullable;

public class BookDetailsFragment extends Fragment {

    public static final String ARG_BOOK = "book"; // Clave para pasar el libro al fragmento
    private Book libro;

    public BookDetailsFragment() {
        // Constructor vacío requerido por Android
    }

    // Metodo para crear una nueva instancia del fragmento con un libro como argumento
    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book); // Agrega el libro al Bundle
        fragment.setArguments(args); // Asigna los argumentos al fragmento
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            libro = (Book) getArguments().getSerializable(ARG_BOOK); // Recupera el libro
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        // Referencias a los elementos de la interfaz
        ImageView fotografia = view.findViewById(R.id.bookImage);
        TextView titulo = view.findViewById(R.id.bookTitle);
        TextView autor = view.findViewById(R.id.bookAuthor);
        TextView editorial = view.findViewById(R.id.bookPublisher);
        TextView fecha_publicacion = view.findViewById(R.id.bookPublicationDate);
        TextView descripcion = view.findViewById(R.id.bookDescription);

        // Si el libro no es nulo, asigna los valores a los elementos de la interfaz
        if (libro != null) {
            // Carga la imagen del libro usando Glide
            Glide.with(this)
                    .load(libro.getFotografia())
                    .placeholder(R.drawable.placeholder_image)
                    .into(fotografia);

            // Establece los valores en los elementos de texto
            titulo.setText(libro.getTitulo() != null ? libro.getTitulo() : "Sin título");
            autor.setText(libro.getAutor() != null ? "Autor: " + libro.getAutor() : "Autor desconocido");
            editorial.setText(libro.getEditorial() != null ? "Editorial: " + libro.getEditorial() : "Editorial no especificada");
            fecha_publicacion.setText(libro.getFecha_publicacion() != null ? "Fecha: " + libro.getFecha_publicacion() : "Fecha no disponible");
            descripcion.setText(libro.getDescripcion() != null ? libro.getDescripcion() : "Descripción no disponible");
        }

        return view; // Devuelve la vista creada
    }
}
