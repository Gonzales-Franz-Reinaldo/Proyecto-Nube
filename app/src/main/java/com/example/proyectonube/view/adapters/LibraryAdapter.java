package com.example.proyectonube.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    private final List<Book> libros;
    private final Context context;
    private final OnBookActionListener actionListener;

    public interface OnBookActionListener {
        void onEditBook(Book libro);
        void onDeleteBook(Book libro);
    }


    public LibraryAdapter(List<Book> libros, Context context, OnBookActionListener actionListener) {
        this.libros = libros;
        this.context = context;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.library_item_card, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Book libro = libros.get(position);

        holder.titulo.setText(libro.getTitulo());
        holder.autor.setText(libro.getAutor());
        holder.editorial.setText(libro.getEditorial());
        holder.fecha_publicacion.setText(libro.getFecha_publicacion());

        Glide.with(context)
                .load(libro.getFotografia())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.fotografia);

        // Configurar botón de editar
        holder.btnEditBook.setOnClickListener(v -> {
            if (libro.getId() != null) {
                if (actionListener != null) {
                    actionListener.onEditBook(libro);
                }
            } else {
                Toast.makeText(context, "Error: El libro no tiene un ID válido", Toast.LENGTH_SHORT).show();
            }
        });


        // Configurar botón de eliminar
        holder.btnDeleteBook.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDeleteBook(libro);
            }
        });

    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    static class LibraryViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, autor, editorial, fecha_publicacion;
        ImageView fotografia;
        ImageButton btnEditBook, btnDeleteBook;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.libraryBookTitle);
            autor = itemView.findViewById(R.id.libraryBookAuthor);
            editorial = itemView.findViewById(R.id.libraryBookPublisher);
            fecha_publicacion = itemView.findViewById(R.id.libraryBookFecha);
            fotografia = itemView.findViewById(R.id.libraryBookFotografia);

            btnEditBook = itemView.findViewById(R.id.btnEditBook);
            btnDeleteBook = itemView.findViewById(R.id.btnDeleteBook);
        }
    }
}
