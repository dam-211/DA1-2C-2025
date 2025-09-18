package com.example.productcard; // Reemplaza con tu nombre de paquete

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 1. Declarar las vistas con las que vamos a interactuar
    private ImageView productImageView;
    private TextView titleTextView;
    private TextView priceTextView;
    private Button addToCartButton;
    private Button buyNowButton;
    private ImageView favoriteIcon; // Asumiendo que agregaste el ícono de favorito
    private ImageView shareIcon;    // Asumiendo que agregaste el ícono de compartir

    // Variable para rastrear el estado de "favorito"
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Inicializar las vistas (conectar las variables con los IDs del XML)

        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        addToCartButton = findViewById(R.id.addToCartButton);
        buyNowButton = findViewById(R.id.buyNowButton);
        favoriteIcon = findViewById(R.id.favoriteIcon);
        shareIcon = findViewById(R.id.shareIcon);      

        // 3. Configurar los "Click Listeners" para responder a las interacciones
        setupClickListeners();
    }

    private void setupClickListeners() {

        // Lógica para el botón "Añadir al Carrito"
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Muestra un mensaje temporal (Toast)
                Toast.makeText(MainActivity.this, "¡Producto añadido al carrito!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica para el botón "Comprar Ahora"
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Iniciando proceso de compra...", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica para el ícono de "Favorito"
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite; // Invierte el estado actual

                if (isFavorite) {
                    // Si ahora es favorito, muestra el corazón relleno
                    favoriteIcon.setImageResource(R.drawable.ic_favorite_filled);
                    Toast.makeText(MainActivity.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    // Si no es favorito, muestra el corazón con borde
                    favoriteIcon.setImageResource(R.drawable.ic_favorite_border);
                    Toast.makeText(MainActivity.this, "Quitado de favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lógica para el ícono de "Compartir"
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para compartir contenido
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                // Prepara el texto que se va a compartir
                String productTitle = titleTextView.getText().toString();
                String productPrice = priceTextView.getText().toString();
                String shareText = "¡Mira este increíble producto! " + productTitle + " por solo " + productPrice + ".";
                
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                // Muestra el menú de compartir de Android
                startActivity(Intent.createChooser(shareIntent, "Compartir producto vía..."));
            }
        });
    }
}
