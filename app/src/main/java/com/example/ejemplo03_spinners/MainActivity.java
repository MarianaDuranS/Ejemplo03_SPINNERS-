package com.example.ejemplo03_spinners;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerGrados1, spinnerGrados2;
    private EditText cajaGrados1, cajaResultados;
    private String unidad1,unidad2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerGrados1=findViewById(R.id.spinner_grados1);
        spinnerGrados2=findViewById(R.id.spinner_grados2);
        cajaGrados1 = findViewById(R.id.caja_grados1);
        cajaResultados = findViewById(R.id.caja_resultados);


        String nombreGrados[]={"Celsius","Fahrenheit","Kelvin","Rankin"};
        ArrayAdapter<String> adaptadorGrados1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreGrados);
        spinnerGrados1.setAdapter(adaptadorGrados1);

        ArrayAdapter<String> adaptadorGrados2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreGrados);
        spinnerGrados2.setAdapter(adaptadorGrados2);

        spinnerGrados1.setOnItemSelectedListener(this);
        spinnerGrados2.setOnItemSelectedListener(this);

        cajaResultados.setFocusable(false);
        cajaResultados.setClickable(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinner_grados1) {
            unidad1 = adapterView.getItemAtPosition(i).toString();
            actualizarSpinnerFinal(unidad1);

            cajaResultados.setText("");
        } else if (adapterView.getId() == R.id.spinner_grados2) {
            unidad2 = adapterView.getItemAtPosition(i).toString();
            if (unidad1 != null && unidad2 != null && !cajaGrados1.getText().toString().isEmpty()) {
                convertirTemperatura();
            }
        }


    }

    private void actualizarSpinnerFinal(String unidadSeleccionada) {
        String[] nombreGrados;
        if (unidadSeleccionada != null) {
            if (unidadSeleccionada.equals("Celsius")) {
                nombreGrados = new String[]{"Fahrenheit", "Kelvin", "Rankin"};
            } else if (unidadSeleccionada.equals("Fahrenheit")) {
                nombreGrados = new String[]{"Celsius", "Kelvin", "Rankin"};
            } else if (unidadSeleccionada.equals("Kelvin")) {
                nombreGrados = new String[]{"Celsius", "Fahrenheit", "Rankin"};
            } else if (unidadSeleccionada.equals("Rankin")) {
                nombreGrados = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
            } else {
                nombreGrados = new String[]{"Celsius", "Fahrenheit", "Kelvin", "Rankin"};
            }
        } else {
            nombreGrados = new String[]{"Celsius", "Fahrenheit", "Kelvin", "Rankin"};
        }

        ArrayAdapter<String> adaptadorGrados2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                nombreGrados);
        spinnerGrados2.setAdapter(adaptadorGrados2);

        boolean found = false;
        if (unidad2 != null) {
            for (String grado : nombreGrados) {
                if (grado.equals(unidad1)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                unidad2 = null;
                cajaResultados.setText("");
            }
        }
    }

    private void convertirTemperatura() {
        String valorStr = cajaGrados1.getText().toString();

        if (valorStr.isEmpty()) {
            cajaResultados.setText("");
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            double resultado = 0.0;


            if (unidad1.equals("Celsius")) {
                if (unidad2.equals("Fahrenheit")) {
                    resultado = (valor * 9 / 5) + 32;
                } else if (unidad2.equals("Kelvin")) {
                    resultado = valor + 273.15;
                } else if (unidad2.equals("Rankin")) {
                    resultado = (valor + 273.15) * 9 / 5;
                } else {
                    resultado = valor;
                }
            } else if (unidad2.equals("Fahrenheit")) {
                if (unidad2.equals("Celsius")) {
                    resultado = (valor - 32) * 5 / 9;
                } else if (unidad2.equals("Kelvin")) {
                    resultado = (valor - 32) * 5 / 9 + 273.15;
                } else if (unidad2.equals("Rankin")) {
                    resultado = valor + 459.67;
                } else {
                    resultado = valor;
                }
            } else if (unidad1.equals("Kelvin")) {
                if (unidad2.equals("Celsius")) {
                    resultado = valor - 273.15;
                } else if (unidad2.equals("Fahrenheit")) {
                    resultado = (valor - 273.15) * 9 / 5 + 32;
                } else if (unidad2.equals("Rankin")) {
                    resultado = valor * 9 / 5;
                } else {
                    resultado = valor;
                }
            } else if (unidad1.equals("Rankin")) {
                if (unidad2.equals("Celsius")) {
                    resultado = (valor - 491.67) * 5 / 9;
                } else if (unidad2.equals("Fahrenheit")) {
                    resultado = valor - 459.67;
                } else if (unidad2.equals("Kelvin")) {
                    resultado = valor * 5 / 9;
                } else {
                    resultado = valor;
                }
            }

            cajaResultados.setText(String.format("%.2f", resultado));

        } catch (NumberFormatException e) {
            cajaResultados.setText("Error");
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}