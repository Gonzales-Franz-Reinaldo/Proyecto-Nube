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

public class BookListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BookAdapter adapter;
    private List<Book> listaLibros = new ArrayList<>();
    private BookRepository bookRepository;

    public interface OnBookSelectedListener {
        void onBookSelected(Book book);
    }

    private OnBookSelectedListener listener;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof OnBookSelectedListener) {
            listener = (OnBookSelectedListener) getActivity();
        } else {
            throw new RuntimeException("La actividad debe implementar OnBookSelectedListener");
        }

        bookRepository = new BookRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        progressBar = view.findViewById(R.id.progressBar);

        setupRecyclerView();
        loadBooks();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columnas
        adapter = new BookAdapter(listaLibros, getContext(), book -> {
            if (listener != null) {
                listener.onBookSelected(book);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void loadBooks() {
        progressBar.setVisibility(View.VISIBLE);
        bookRepository.getAllBooks(new BookRepository.OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> libros) {
                progressBar.setVisibility(View.GONE);
                if (libros != null) {
                    listaLibros.clear();
                    listaLibros.addAll(libros);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No se encontraron libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al cargar los libros: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
