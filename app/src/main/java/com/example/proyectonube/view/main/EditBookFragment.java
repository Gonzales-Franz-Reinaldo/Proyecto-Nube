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

public class EditBookFragment extends Fragment {
    private EditText etTitulo, etAutor, etEditorial, etFechaPublicacion, etDescripcion, etFotografia;
    private Button btnEditarLibro;
    private BookRepository bookRepository;
    private Book bookToEdit;

    public static EditBookFragment newInstance(Book book) {
        EditBookFragment fragment = new EditBookFragment();
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_book, container, false);

        // Inicializar componentes
        etTitulo = view.findViewById(R.id.etTitulo);
        etAutor = view.findViewById(R.id.etAutor);
        etEditorial = view.findViewById(R.id.etEditorial);
        etFechaPublicacion = view.findViewById(R.id.etFechaPublicacion);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        etFotografia = view.findViewById(R.id.etFotografia);
        btnEditarLibro = view.findViewById(R.id.btnEditarLibro);

        // Inicializar repositorio
        bookRepository = new BookRepository();

        // Rellenar los campos con los datos actuales del libro
        if (getArguments() != null) {
            bookToEdit = (Book) getArguments().getSerializable("book");

            if (bookToEdit != null) {
                etTitulo.setText(bookToEdit.getTitulo());
                etAutor.setText(bookToEdit.getAutor());
                etEditorial.setText(bookToEdit.getEditorial());
                etFechaPublicacion.setText(bookToEdit.getFecha_publicacion());
                etDescripcion.setText(bookToEdit.getDescripcion());
                etFotografia.setText(bookToEdit.getFotografia());
            }
        }


        // Configurar botón de editar libro
        btnEditarLibro.setOnClickListener(v -> editarLibro());

        return view;
    }

    private void editarLibro(){
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

        if (bookToEdit == null || TextUtils.isEmpty(bookToEdit.getId())) {
            Toast.makeText(getContext(), "Error: No se pudo identificar el libro a editar", Toast.LENGTH_SHORT).show();
            return;
        }


        // Actualizar los datos del libro
        bookToEdit.setTitulo(titulo);
        bookToEdit.setAutor(autor);
        bookToEdit.setEditorial(editorial);
        bookToEdit.setFecha_publicacion(fechaPublicacion);
        bookToEdit.setDescripcion(descripcion);
        bookToEdit.setFotografia(fotografia);

        // Guardar cambios en Firestore
        bookRepository.updateBook(bookToEdit, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Libro actualizado correctamente", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack(); // Volver al listado
            } else {
                Toast.makeText(getContext(), "Error al actualizar libro", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
