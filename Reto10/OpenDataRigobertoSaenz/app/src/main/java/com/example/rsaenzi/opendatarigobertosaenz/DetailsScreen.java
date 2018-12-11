package com.example.rsaenzi.opendatarigobertosaenz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsScreen extends Activity {

    private TextView city;
    private TextView convenio;
    private TextView convocatoria;
    private TextView estado;
    private TextView genero;
    private TextView nivel;
    private TextView valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen_layout);

        city = (TextView) findViewById(R.id.City);
        convenio = (TextView) findViewById(R.id.Convenio);
        convocatoria = (TextView) findViewById(R.id.Convocatoria);
        estado = (TextView) findViewById(R.id.Estado);
        genero = (TextView) findViewById(R.id.Genero);
        nivel = (TextView) findViewById(R.id.Nivel);
        valor = (TextView) findViewById(R.id.Valor);

        String paramCity = getIntent().getStringExtra("city");
        String paramConvenio = getIntent().getStringExtra("convenio");
        String paramConvocatoria = getIntent().getStringExtra("convocatoria");
        String paramEstado = getIntent().getStringExtra("estado");
        String paramGenero = getIntent().getStringExtra("genero");
        String paramNivel = getIntent().getStringExtra("nivel");
        String paramValor = getIntent().getStringExtra("valor");


        city.setText("City: " + paramCity);
        convenio.setText("Convenio: " + paramConvenio);
        convocatoria.setText("Convocatoria: " + paramConvocatoria);
        estado.setText("Estado: " + paramEstado);
        genero.setText("Genero: " + paramGenero);
        nivel.setText("Nivel: " + paramNivel);
        valor.setText("Valor: " + paramValor);

    }

}
