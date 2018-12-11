package com.example.rsaenzi.opendatarigobertosaenz;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.rsaenzi.opendatarigobertosaenz.Model.ScholarshipWinner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends ListActivity {

    public static final String BASE_URL = "https://www.datos.gov.co/";
    private ArrayAdapter<ScholarshipWinner> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ScholarshipWinner> emptyList = new ArrayList<ScholarshipWinner>();

        adapter = new ArrayAdapter<ScholarshipWinner>(this, android.R.layout.simple_list_item_1, emptyList);
        setListAdapter(adapter);

        final ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ScholarshipWinner selection = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailsScreen.class);
                intent.putExtra("city", selection.getCiudadIes());
                intent.putExtra("convenio", selection.getConvenio());
                intent.putExtra("convocatoria", selection.getConvocatoria());
                intent.putExtra("estado", selection.getEstadoDeLaCondonaciN());
                intent.putExtra("genero", selection.getGenero());
                intent.putExtra("nivel", selection.getTipoDeEducaciN());
                intent.putExtra("valor", selection.getValorACondonar());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MyApiEndpointInterfaces apiService = retrofit.create(MyApiEndpointInterfaces.class);
        Call<List<ScholarshipWinner>> call = apiService.getAllScholarshipWinners();

        call.enqueue(new Callback<List<ScholarshipWinner>>() {

            @Override
            public void onResponse(Call<List<ScholarshipWinner>> call, Response<List<ScholarshipWinner>> response) {

                int statusCode = response.code();
                List<ScholarshipWinner> myList = response.body();

                adapter.clear();
                adapter.addAll(myList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ScholarshipWinner>> call, Throwable t) {
                adapter.clear();
                adapter.notifyDataSetChanged();
            }

        });
    }
}