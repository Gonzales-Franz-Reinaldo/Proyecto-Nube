package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private EditText searchBar;
    private BookAdapter adapter; // Adaptador para mostrar los libros
    private List<Book> listaLibros = new ArrayList<>(); // Lista de libros obtenidos
    private List<Book> listaFiltrada = new ArrayList<>(); // Lista de libros filtrados
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
        searchBar = getActivity().findViewById(R.id.searchInput); // Inicializa el EditText

        setupRecyclerView(); // Configura el RecyclerView
        setupSearchBar(); // Configura la barra de búsqueda
        loadBooks(); // Carga los libros

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new BookAdapter(listaFiltrada, getContext(), book -> {
            if (listener != null) {
                listener.onBookSelected(book);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchBar(){
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarLibros(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    listaFiltrada.clear();
                    listaFiltrada.addAll(libros);
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


    private void filtrarLibros(String texto) {
        listaFiltrada.clear();

        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaLibros); // Mostrar todos los libros si no hay texto
        } else {
            texto = texto.toLowerCase(); // Convertir la consulta a minúsculas para una búsqueda insensible a mayúsculas
            for (Book libro : listaLibros) {
                if (libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(texto)) {
                    listaFiltrada.add(libro); // Si el título contiene la consulta, agregar a la lista
                } else if (libro.getAutor() != null && libro.getAutor().toLowerCase().contains(texto)) {
                    listaFiltrada.add(libro); // Si el autor contiene la consulta, agregar a la lista
                }
            }
        }

        adapter.notifyDataSetChanged(); // Notificar al adaptador los cambios
    }

}