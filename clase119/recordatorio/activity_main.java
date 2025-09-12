package ar.edu.practicaviews;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilTitulo;
    private TextInputEditText etTitulo;
    private RadioGroup rgPrioridad;
    private MaterialSwitch swRepetir;
    private MaterialButton btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilTitulo = findViewById(R.id.tilTitulo);
        etTitulo  = findViewById(R.id.etTitulo);
        rgPrioridad = findViewById(R.id.rgPrioridad);
        swRepetir  = findViewById(R.id.swRepetir);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v-> {

            String titulo = etTitulo.getText() != null ? etTitulo.getText().toString().trim() : "";

            if(titulo.isEmpty()){
                tilTitulo.setError("Ingresa un titulo válido");
                etTitulo.requestFocus();
                return;
            }else{

                tilTitulo.setError(null);
            }

            //Trabajamos con los RadioGroups

            int seleccionadoId = rgPrioridad.getCheckedRadioButtonId();
            RadioButton rbSeleccionado = findViewById(seleccionadoId);
            String prioridad = rbSeleccionado !=null ? rbSeleccionado.getText().toString() : "Baja";

            //Switch...

            boolean repetir = swRepetir.isChecked();

            String mensajeSalida = "Recordatorio guardado! \n"+
                    "Titulo: " + titulo + "\n" +
                    "Prioridad: " + prioridad + "\n" +
                    "Repetir: " + (repetir ? "Si" : "No");

           // Snackbar.make(v, mensajeSalida, Snackbar.LENGTH_LONG).show();
            Toast.makeText(this, mensajeSalida, Toast.LENGTH_SHORT).show();
        });

    }
}
