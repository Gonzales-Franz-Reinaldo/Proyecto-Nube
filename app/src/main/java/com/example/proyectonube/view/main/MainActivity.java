package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectonube.R;
import com.example.proyectonube.model.Book;
import com.example.proyectonube.view.crud.BookDetailsActivity;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadFragment(new BookListFragment());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBookSelected(Book book) {
        BookDetailsFragment detailsFragment = BookDetailsFragment.newInstance(book);
        loadFragment(detailsFragment);
    }

    public void loadFragment(Fragment fragment) {

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar el fragmento", Toast.LENGTH_SHORT).show();
        }
    }
}