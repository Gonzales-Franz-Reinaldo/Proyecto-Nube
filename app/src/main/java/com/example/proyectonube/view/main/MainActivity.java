package com.example.proyectonube.view.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener {

    private LinearLayout searchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura el modo de pantalla completa extendida
        EdgeToEdge.enable(this);

        // Configura el diseño principal
        setContentView(R.layout.activity_main);

        // Carga el fragmento inicial que muestra la lista de libros
        loadFragment(new BookListFragment());

        // Ajusta el diseño para que respete las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new BookListFragment();
            } else if (itemId == R.id.nav_library) {
                selectedFragment = new LibraryFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }


            if(selectedFragment != null){
                loadFragment(selectedFragment);
            }

            return true;
        });

    }

    @Override
    public void onBookSelected(Book book) {
        // Crea una instancia del fragmento de detalles del libro
        BookDetailsFragment detailsFragment = BookDetailsFragment.newInstance(book);

        // Carga el fragmento de detalles en el contenedor principal
        loadFragment(detailsFragment);
    }

    public void loadFragment(Fragment fragment) {
        try {
            // Inicia una transacción para reemplazar el fragmento
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, fragment); // Reemplaza el contenido actual
            transaction.addToBackStack(null); // Agrega la transacción a la pila de retroceso
            transaction.commit(); // Aplica los cambios
        } catch (Exception e) {
            // Manejo de errores en caso de fallo al cargar el fragmento
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar el fragmento", Toast.LENGTH_SHORT).show();
        }
    }
}
