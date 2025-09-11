package ar.edu.practicaviews;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputLayout tilMonto = findViewById(R.id.tilMonto);
        TextInputEditText etMonto = findViewById(R.id.etMonto);
        Spinner spMoneda = findViewById(R.id.spMoneda);
        MaterialButton btnGuardar = findViewById(R.id.btnGuardar);

        //Configuramos el Spinner de las monedas

        List<String> monedas = Arrays.asList("ARS", "USD", "EUR", "BTC", "YEN");
        ArrayAdapter<String> adapterMonedas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monedas );
        spMoneda.setAdapter(adapterMonedas);

        btnGuardar.setOnClickListener(v-> {

            String montoTxt;
            if(etMonto.getText() != null){
                montoTxt = etMonto.getText().toString().trim();
            }else{
                montoTxt = "";
            }

            double montoDbl;

            try{
                montoDbl = Double.parseDouble(montoTxt);
            } catch (NumberFormatException e) {

                montoDbl = -1.0;
            }

            if(montoDbl <=0.0){
                tilMonto.setError("Ingresa un monto válido!");
                etMonto.requestFocus();
                return;
            }

            tilMonto.setError(null);

            Snackbar.make(btnGuardar, "Gasto guardado " + montoTxt, Snackbar.LENGTH_SHORT).show();

        });

    }
}
