package com.example.proyectonube.view.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;
import com.example.proyectonube.model.BookRepository;
import com.example.proyectonube.view.adapters.LibraryAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LibraryAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private BookRepository bookRepository;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLibrary);
        progressBar = view.findViewById(R.id.progressBar);

        FloatingActionButton fabAddBook = view.findViewById(R.id.fabAddBook);

        // Configurar el evento del botón flotante
        fabAddBook.setOnClickListener(v -> {
            // Reemplaza el fragmento actual por el de agregar libro
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, new AddBookFragment());
            transaction.addToBackStack(null); // Permitir navegación hacia atrás
            transaction.commit();
        });

        setupRecyclerView();
        loadBooks();

        return view;
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new LibraryAdapter(bookList, getContext(), new LibraryAdapter.OnBookActionListener(){
            @Override
            public void onEditBook(Book book){
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, EditBookFragment.newInstance(book));
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onDeleteBook(Book book){
                showDeleteConfirmationDialog(book);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void loadBooks(){
        progressBar.setVisibility(View.VISIBLE);
        bookRepository = new BookRepository();

        bookRepository.getAllBooks(new BookRepository.OnBooksLoadedListener(){
            @Override
            public void onBooksLoaded(List<Book> libros){
                progressBar.setVisibility(View.GONE);

                if(libros != null){
                    bookList.clear();
                    bookList.addAll(libros);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No se encontraron libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al cargar los libros", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog(Book book){
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar libro")
                .setMessage("¿Estás seguro de que deseas eliminar este libro?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    deleteBook(book);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void deleteBook(Book book){
        progressBar.setVisibility(View.VISIBLE);
        bookRepository.deleteBook(book.getId(), task -> {
            progressBar.setVisibility(View.GONE);
            if(task.isSuccessful()){
                Toast.makeText(getContext(), "Libro eliminado", Toast.LENGTH_SHORT).show();
                bookList.remove(book);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Error al eliminar el libro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
