package com.example.rsaenzi.opendatarigobertosaenz;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.rsaenzi.opendatarigobertosaenz.Model.ScholarshipWinner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
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

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, emptyList);
        setListAdapter(adapter);
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
                Log.i("Open Data", "Status Code: " + statusCode);

                List<ScholarshipWinner> myList = response.body();
                Log.i("Open Data", "Response: " + myList);
            }

            @Override
            public void onFailure(Call<List<ScholarshipWinner>> call, Throwable t) {
                Log.e("Open Data", "Error");
            }

        });

        /*
        try {

            Response<List<ScholarshipWinner>> response = call.execute();

        } catch (IOException e ){
            // handle error
        }
        */

    }
}

