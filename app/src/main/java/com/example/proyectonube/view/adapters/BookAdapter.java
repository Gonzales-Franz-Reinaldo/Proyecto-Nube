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

// Adaptador personalizado para RecyclerView que muestra una lista de libros
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> libros; // Lista de libros que se mostrarán
    private Context context; // Contexto de la actividad o fragmento
    private OnBookClickListener listener; // Interfaz para manejar clics en los libros

    // Interfaz para manejar clics en los elementos del RecyclerView
    public interface OnBookClickListener {
        void onBookClick(Book libro); // Metodo que se ejecutará al hacer clic en un libro
    }

    // Constructor para inicializar la lista de libros, el contexto y el listener
    public BookAdapter(List<Book> libros, Context context, OnBookClickListener listener){
        this.libros = libros;
        this.context = context;
        this.listener = listener; // Asignar el listener para manejar los clics
    }

    // Metodo para crear nuevos elementos del RecyclerView (ViewHolder)
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño de la tarjeta de libro (book_item_card.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.book_item_card, parent, false);
        return new BookViewHolder(view); // Retorna un nuevo ViewHolder con la vista inflada
    }

    // Metodo para vincular datos a un elemento del RecyclerView
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Obtiene el libro en la posición actual
        Book libro = libros.get(position);

        // Configura los datos del libro en las vistas correspondientes
        holder.titulo.setText(libro.getTitulo());
        holder.autor.setText(libro.getAutor());
        holder.fecha_publicacion.setText(libro.getFecha_publicacion());

        // Usa Glide para cargar la imagen del libro desde una URL
        Glide.with(context)
                .load(libro.getFotografia())
                .placeholder(R.drawable.placeholder_image) // Imagen de marcador de posición mientras se carga
                .into(holder.fotografia); // Asigna la imagen al ImageView

        // Configura el listener para manejar clics en el elemento actual
        holder.itemView.setOnClickListener(v -> {
            listener.onBookClick(libro); // Llama al metodo onBookClick con el libro seleccionado
        });
    }

    // Devuelve el número total de elementos en la lista
    @Override
    public int getItemCount() {
        return libros.size();
    }

    // Clase interna que representa los elementos del RecyclerView (ViewHolder)
    static class BookViewHolder extends RecyclerView.ViewHolder {
        // Vistas individuales dentro de la tarjeta de libro
        TextView titulo, autor, fecha_publicacion; // Título y fecha de publicación del libro
        ImageView fotografia; // Imagen del libro

        // Constructor que recibe la vista inflada y asigna las vistas específicas
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asocia las vistas del layout book_item_card.xml con las variables de la clase
            titulo = itemView.findViewById(R.id.cardTitulo);
            autor = itemView.findViewById(R.id.cardAutor);
            fecha_publicacion = itemView.findViewById(R.id.cardFecha_publicacion);
            fotografia = itemView.findViewById(R.id.cardFotografia);
        }
    }
}