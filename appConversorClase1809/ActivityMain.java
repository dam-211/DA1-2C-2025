package ar.edu.contraint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Constantes para SharedPreference
    public static final String SHARED_PREFS_FILE = "ConversorPrefs";
    public static final String LAST_CONVERSION_KEY = "ultimaConversion";

    //Llamada a las vistas de la UI

    private EditText editTextAmount;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textViewResult, textViewLastConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos las vistas

        editTextAmount = findViewById(R.id.editTextAmount);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConverter);
        textViewResult = findViewById(R.id.textViewResult);
        textViewLastConversion = findViewById(R.id.textViewLastConversion);

        setupSpinners();

       loadLastConversion();

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency();
            }
        });

    }

    private void setupSpinners() {

        //Crear un ArrayAdapter usando un array de string y un layout de spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, android.R.layout.simple_spinner_item);
        //Especificar el layout a usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Aplicamos el adaptador a los DOS spinners
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    private void convertCurrency() {

        String amountString = editTextAmount.getText().toString();

        //Valdar que el campo monto no este vacio
        if (amountString.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un monto valido", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountString);
            String fromCurrency = spinnerFrom.getSelectedItem().toString();
            String toCurrency = spinnerTo.getSelectedItem().toString();

            double rate = getConversionRate(fromCurrency, toCurrency);
            double result = amount * rate;

            String resultString = String.format(Locale.US, "%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency);

            textViewResult.setText(resultString);

            saveLastConversion(resultString);
        } catch (Exception e) {

            Toast.makeText(this, "Monto Invalido", Toast.LENGTH_SHORT).show();
        }


    }
    private double getConversionRate(String from, String to) {
        //Vamos a usar valores fijos para el TC

        if(from.equals(to)){
            return 1.0;
        }

        if(from.equals("USD") && to.equals("EUR")) return 0.89;
        if(from.equals("ARS") && to.equals("USD")) return 0.00066;
        return 0.0;
    }

    private void saveLastConversion(String conversion) {
        //Obtener una instancia de SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        //Llamo al editor
        SharedPreferences.Editor editor = prefs.edit();
        //Guardar el string!!

        editor.putString(LAST_CONVERSION_KEY, conversion);
        //Tengo que aplicar los cambios

        editor.apply();
        //Hago update!

        textViewLastConversion.setText("Ultima conversion: " + conversion);


    }


    private void loadLastConversion(){

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        String lastConversion = prefs.getString(LAST_CONVERSION_KEY, "No hay valores anteriores");
        textViewLastConversion.setText(lastConversion);
    }
}
