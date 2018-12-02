package com.example.rsaenz.emsrigobertosaenz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;

public class MainActivity extends AppCompatActivity{

    private Button addCompanyButton;
    private Button editCompanyButton;
    private Button deleteCompanyButton;
    private Button viewAllCompaniesButton;
    private CompanyOperations companyOps;
    private static final String EXTRA_COMPANY_ID = "com.rsaenzi.companyapp.companyId";
    private static final String EXTRA_ADD_UPDATE = "com.rsaenzi.companyapp.add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addCompanyButton = (Button) findViewById(R.id.button_add_company);
        editCompanyButton = (Button) findViewById(R.id.button_edit_company);
        deleteCompanyButton = (Button) findViewById(R.id.button_delete_company);
        viewAllCompaniesButton = (Button)findViewById(R.id.button_view_companies);

        addCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddUpdateCompany.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });

        editCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCompany();
            }
        });

        deleteCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCompany();
            }
        });

        viewAllCompaniesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllCompanies.class);
                startActivity(i);
            }
        });

        companyOps = new CompanyOperations(MainActivity.this);
    }

    public void editCompany(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        if (userInput.getText().toString().length() == 0) {
                            return;
                        }

                        // get user input and set it to result
                        // edit text
                        Intent i = new Intent(MainActivity.this,AddUpdateCompany.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_COMPANY_ID, Long.parseLong(userInput.getText().toString()));
                        startActivity(i);
                    }
                }).create()
                .show();

    }


    public void deleteCompany(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_emp_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);

        final Context thisScreen = this;

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        if (userInput.getText().toString().length() == 0) {
                            return;
                        }

                        new AlertDialog.Builder(thisScreen)
                                .setTitle("Confirmation")
                                .setMessage("Do you really want to delete the Company?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        realDelete(userInput.getText().toString());
                                    }})

                                .setNegativeButton(android.R.string.no, null).show();
                    }
                }).create()
                .show();
    }

    private void realDelete(String companyId) {

        int result = 0;

        try {

            companyOps.open();
            result = companyOps.removeCompany(companyOps.getCompany(Long.parseLong(companyId)));

            if(result != 0) {
                Toast.makeText(MainActivity.this,"Company removed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,"That company id does not exist, please try again", Toast.LENGTH_SHORT).show();
            }

        } catch(Exception e) {
            Toast.makeText(MainActivity.this,"That company id does not exist, please try again", Toast.LENGTH_SHORT).show();

        } finally {
            companyOps.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        companyOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        companyOps.close();

    }
}