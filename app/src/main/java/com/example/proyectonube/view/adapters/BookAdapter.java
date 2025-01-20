package com.example.proyectonube.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> libros;
    private Context context;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book libro);
    }

    public BookAdapter(List<Book> libros, Context context, OnBookClickListener listener){
        this.libros = libros;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item_card, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book libro = libros.get(position);

        holder.titulo.setText(libro.getTitulo());
        holder.fecha_publicacion.setText(libro.getFecha_publicacion());

        // Para cargar imágenes
        Glide.with(context)
                .load(libro.getFotografia())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.fotografia);

        holder.itemView.setOnClickListener(v -> {
            listener.onBookClick(libro);
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, fecha_publicacion;
        ImageView fotografia;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.cardTitulo);
            fecha_publicacion = itemView.findViewById(R.id.cardFecha_publicacion);
            fotografia = itemView.findViewById(R.id.cardFotografia);
        }
    }
}
