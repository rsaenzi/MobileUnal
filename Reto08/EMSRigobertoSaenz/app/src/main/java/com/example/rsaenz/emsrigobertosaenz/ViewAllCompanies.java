package com.example.rsaenz.emsrigobertosaenz;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;
import com.example.rsaenz.emsrigobertosaenz.Entity.CompanyType;

import java.util.List;

public class ViewAllCompanies extends ListActivity{

    private CompanyOperations companyOps;
    private List<Company> companies;
    private ArrayAdapter<Company> adapter;

    private RadioGroup radioGroupFilter;
    private RadioButton radioButtonAll, radioButtonConsultoria, radioButtonDesarrollo, radioButtonFabrica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_companies);

        companyOps = new CompanyOperations(this);
        companyOps.open();
        companies = companyOps.getAllCompanies(null);
        companyOps.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, companies);
        setListAdapter(adapter);


        radioGroupFilter = (RadioGroup) findViewById(R.id.radio_filter_type);
        radioButtonAll = (RadioButton) findViewById(R.id.filter_type_all);
        radioButtonConsultoria = (RadioButton) findViewById(R.id.filter_type_consultoria);
        radioButtonDesarrollo = (RadioButton) findViewById(R.id.filter_type_desarrollo);
        radioButtonFabrica = (RadioButton) findViewById(R.id.filter_type_fabrica);


        radioGroupFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if (checkedId == R.id.filter_type_all) {
                    loadList(null);

                } else if (checkedId == R.id.filter_type_consultoria) {
                    loadList(CompanyType.Consultoria);

                } else if (checkedId == R.id.filter_type_desarrollo) {
                    loadList(CompanyType.DesarrolloALaMedida);

                } else if (checkedId == R.id.filter_type_fabrica) {
                    loadList(CompanyType.FabricaDeSoftware);
                }
            }

        });
    }

    private void loadList(CompanyType type) {
        
        companyOps.open();
        companies = companyOps.getAllCompanies(type);
        companyOps.close();

        adapter.clear();
        adapter.addAll(companies);
        adapter.notifyDataSetChanged();
    }
}