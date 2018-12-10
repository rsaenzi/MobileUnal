package com.example.rsaenzi.opendatarigobertosaenz;


import com.example.rsaenzi.opendatarigobertosaenz.Model.ScholarshipWinner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiEndpointInterfaces {

    // Request method and URL specified in the annotation

    @GET("resource/ga79-5yv4.json")
    Call<List<ScholarshipWinner>> getAllScholarshipWinners();

}
