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

import java.io.Serializable;

import javax.annotation.Nullable;

public class BookDetailsFragment extends Fragment {

    public static final String ARG_BOOK = "book";

    private Book libro;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            libro = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        // Referencias a los elementos de la interfaz
        ImageView fotografia = view.findViewById(R.id.bookImage);
        TextView titulo = view.findViewById(R.id.bookTitle);
        TextView autor = view.findViewById(R.id.bookAuthor);
        TextView editorial = view.findViewById(R.id.bookPublisher);
        TextView fecha_publicacion = view.findViewById(R.id.bookPublicationDate);
        TextView descripcion = view.findViewById(R.id.bookDescription);

        // Manejo de datos
        if (libro != null) {
            Glide.with(this)
                    .load(libro.getFotografia())
                    .placeholder(R.drawable.placeholder_image)
                    .into(fotografia);

            titulo.setText(libro.getTitulo() != null ? libro.getTitulo() : "Sin título");
            autor.setText(libro.getAutor() != null ? "Autor: " + libro.getAutor() : "Autor desconocido");
            editorial.setText(libro.getEditorial() != null ? "Editorial: " + libro.getEditorial() : "Editorial no especificada");
            fecha_publicacion.setText(libro.getFecha_publicacion() != null ? "Fecha: " + libro.getFecha_publicacion() : "Fecha no disponible");
            descripcion.setText(libro.getDescripcion() != null ? libro.getDescripcion() : "Descripción no disponible");
        }

        return view;
    }
}
