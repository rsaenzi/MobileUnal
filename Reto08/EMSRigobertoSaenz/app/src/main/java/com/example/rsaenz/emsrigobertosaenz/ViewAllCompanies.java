package com.example.rsaenz.emsrigobertosaenz;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;
import com.example.rsaenz.emsrigobertosaenz.Entity.Employee;
import java.util.List;

public class ViewAllCompanies extends ListActivity{

    private CompanyOperations employeeOps;
    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employees);
        employeeOps = new CompanyOperations(this);
        employeeOps.open();
        employees = employeeOps.getAllEmployees();
        employeeOps.close();
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, employees);
        setListAdapter(adapter);
    }
}