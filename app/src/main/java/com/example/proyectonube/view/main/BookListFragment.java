package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;
import com.example.proyectonube.model.BookRepository;
import com.example.proyectonube.view.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

// Fragmento para mostrar una lista de libros
public class BookListFragment extends Fragment {
    // Referencias a los elementos de la interfaz
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BookAdapter adapter; // Adaptador para mostrar los libros
    private List<Book> listaLibros = new ArrayList<>(); // Lista de libros obtenidos
    private BookRepository bookRepository; // Repositorio para obtener datos de Firestore


    // Interfaz para manejar selecciones de libros
    public interface OnBookSelectedListener {
        void onBookSelected(Book book);
    }

    private OnBookSelectedListener listener; // Listener para comunicar eventos a la actividad

    public BookListFragment() {
        // Constructor vacío requerido por Android
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Verifica que la actividad implemente OnBookSelectedListener
        if (getActivity() instanceof OnBookSelectedListener) {
            listener = (OnBookSelectedListener) getActivity();
        } else {
            throw new RuntimeException("La actividad debe implementar OnBookSelectedListener");
        }
        bookRepository = new BookRepository(); // Inicializa el repositorio
    }

    // Metodo para crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBooks); // Inicializa el RecyclerView
        progressBar = view.findViewById(R.id.progressBar); // Inicializa el ProgressBar

        setupRecyclerView(); // Configura el RecyclerView
        loadBooks(); // Carga los libros

        return view;
    }

    private void setupRecyclerView() {
        // Configura el RecyclerView con un diseño de cuadrícula de 2 columnas
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new BookAdapter(listaLibros, getContext(), book -> {
            if (listener != null) {
                listener.onBookSelected(book); // Notifica la selección de un libro
            }
        });
        recyclerView.setAdapter(adapter); // Conecta el adaptador al RecyclerView
    }

    public void loadBooks() {
        progressBar.setVisibility(View.VISIBLE); // Muestra el ProgressBar
        bookRepository.getAllBooks(new BookRepository.OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> libros) {
                progressBar.setVisibility(View.GONE); // Oculta el ProgressBar
                if (libros != null) {
                    listaLibros.clear(); // Limpia la lista actual
                    listaLibros.addAll(libros); // Agrega los nuevos libros
                    adapter.notifyDataSetChanged(); // Actualiza la interfaz
                } else {
                    Toast.makeText(getContext(), "No se encontraron libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE); // Oculta el ProgressBar
                Toast.makeText(getContext(), "Error al cargar los libros: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
