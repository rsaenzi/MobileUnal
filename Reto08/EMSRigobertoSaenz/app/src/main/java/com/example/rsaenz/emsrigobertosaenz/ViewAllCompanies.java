package com.example.rsaenz.emsrigobertosaenz;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;

import java.util.List;

public class ViewAllCompanies extends ListActivity{

    private CompanyOperations companyOps;
    List<Company> companies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_companies);
        companyOps = new CompanyOperations(this);
        companyOps.open();
        companies = companyOps.getAllCompanies();
        companyOps.close();
        ArrayAdapter<Company> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, companies);
        setListAdapter(adapter);
    }
}