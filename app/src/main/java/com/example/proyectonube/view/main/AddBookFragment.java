package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;
import com.example.proyectonube.model.BookRepository;

public class AddBookFragment extends Fragment {
    private EditText etTitulo, etAutor, etEditorial, etFechaPublicacion, etDescripcion, etFotografia;
    private Button btnAgregarLibro;
    private BookRepository bookRepository;

    public AddBookFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Inicializar componentes
        etTitulo = view.findViewById(R.id.etTitulo);
        etAutor = view.findViewById(R.id.etAutor);
        etEditorial = view.findViewById(R.id.etEditorial);
        etFechaPublicacion = view.findViewById(R.id.etFechaPublicacion);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etFotografia = view.findViewById(R.id.etFotografia);
        btnAgregarLibro = view.findViewById(R.id.btnAgregarLibro);

        // Inicializar repositorio
        bookRepository = new BookRepository();

        // Configurar botón de agregar libro
        btnAgregarLibro.setOnClickListener(v -> agregarLibro());

        return view;
    }

    private void agregarLibro(){
        String titulo = etTitulo.getText().toString().trim();
        String autor = etAutor.getText().toString().trim();
        String editorial = etEditorial.getText().toString().trim();
        String fechaPublicacion = etFechaPublicacion.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fotografia = etFotografia.getText().toString().trim();

        // Validar campos vacíos
        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(autor) || TextUtils.isEmpty(editorial) ||
                TextUtils.isEmpty(fechaPublicacion) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(fotografia)) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Agregar libro
        Book nuevoLibro = new Book(null, fotografia, titulo, autor, editorial, fechaPublicacion, descripcion);

        // Agregar libro a Firestore
        bookRepository.addBook(nuevoLibro, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Libro agregado correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar campos
                etTitulo.setText("");
                etAutor.setText("");
                etEditorial.setText("");
                etFechaPublicacion.setText("");
                etDescripcion.setText("");
                etFotografia.setText("");

                // Volver al listado de libros
                getParentFragmentManager().popBackStack();
            }else {
                Toast.makeText(getContext(), "Error al agregar libro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
