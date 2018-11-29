package com.example.rsaenz.emsrigobertosaenz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;
import com.example.rsaenz.emsrigobertosaenz.Entity.CompanyType;

public class AddUpdateCompany extends AppCompatActivity {

    private static final String EXTRA_COMPANY_ID = "com.rsaenzi.companyapp.companyId";
    private static final String EXTRA_ADD_UPDATE = "com.rsaenzi.companyapp.add_update";

    private String mode;
    private long companyId;
    private CompanyOperations companyData;

    private Company newCompany;
    private Company oldCompany;

    private EditText nameEditText;
    private EditText websiteEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText productsEditText;

    private RadioGroup radioGroupCompanyType;
    private RadioButton radioButtonConsultoria, radioButtonDesarrollo, radioButtonFabrica;

    private Button addUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_company);

        newCompany = new Company();
        oldCompany = new Company();

        nameEditText = (EditText)findViewById(R.id.edit_text_company_name);
        websiteEditText = (EditText)findViewById(R.id.edit_text_company_website);
        phoneEditText = (EditText) findViewById(R.id.edit_text_company_phone);
        emailEditText = (EditText) findViewById(R.id.edit_text_company_email);
        productsEditText = (EditText) findViewById(R.id.edit_text_company_products);

        radioGroupCompanyType = (RadioGroup) findViewById(R.id.radio_company_type);
        radioButtonConsultoria = (RadioButton) findViewById(R.id.radio_type_consultoria);
        radioButtonDesarrollo = (RadioButton) findViewById(R.id.radio_type_desarrollo);
        radioButtonFabrica = (RadioButton) findViewById(R.id.radio_type_fabrica);

        addUpdateButton = (Button)findViewById(R.id.button_add_update_company);

        companyData = new CompanyOperations(this);
        companyData.open();

        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){

            addUpdateButton.setText("Update Company");
            companyId = getIntent().getLongExtra(EXTRA_COMPANY_ID,0);

            initializeCompany(companyId);
        }

        radioGroupCompanyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if (checkedId == R.id.radio_type_consultoria) {
                    newCompany.setType(CompanyType.Consultoria);

                    if(mode.equals("Update")){
                        oldCompany.setType(CompanyType.Consultoria);
                    }

                } else if (checkedId == R.id.radio_type_desarrollo) {
                    newCompany.setType(CompanyType.DesarrolloALaMedida);

                    if(mode.equals("Update")){
                        oldCompany.setType(CompanyType.DesarrolloALaMedida);
                    }

                } else if (checkedId == R.id.radio_type_fabrica) {
                    newCompany.setType(CompanyType.FabricaDeSoftware);

                    if(mode.equals("Update")){
                        oldCompany.setType(CompanyType.FabricaDeSoftware);
                    }
                }
            }

        });

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    newCompany.setName(nameEditText.getText().toString());
                    newCompany.setWebsite(websiteEditText.getText().toString());
                    newCompany.setPhone(phoneEditText.getText().toString());
                    newCompany.setEmail(emailEditText.getText().toString());
                    newCompany.setProducts(productsEditText.getText().toString());

                    if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_consultoria) {
                        newCompany.setType(CompanyType.Consultoria);

                    } else if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_desarrollo) {
                        newCompany.setType(CompanyType.DesarrolloALaMedida);

                    } else if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_fabrica) {
                        newCompany.setType(CompanyType.FabricaDeSoftware);
                    }

                    companyData.addCompany(newCompany);

                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ newCompany.getName() + " has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,MainActivity.class);
                    startActivity(i);

                }else {
                    oldCompany.setName(nameEditText.getText().toString());
                    oldCompany.setWebsite(websiteEditText.getText().toString());
                    oldCompany.setPhone(phoneEditText.getText().toString());
                    oldCompany.setEmail(emailEditText.getText().toString());
                    oldCompany.setProducts(productsEditText.getText().toString());

                    if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_consultoria) {
                        oldCompany.setType(CompanyType.Consultoria);

                    } else if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_desarrollo) {
                        oldCompany.setType(CompanyType.DesarrolloALaMedida);

                    } else if (radioGroupCompanyType.getCheckedRadioButtonId() == R.id.radio_type_fabrica) {
                        oldCompany.setType(CompanyType.FabricaDeSoftware);
                    }

                    companyData.updateCompany(oldCompany);

                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ oldCompany.getName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void initializeCompany(long companyId) {
        oldCompany = companyData.getCompany(companyId);

        nameEditText.setText(oldCompany.getName());
        websiteEditText.setText(oldCompany.getWebsite());
        phoneEditText.setText(oldCompany.getPhone());
        emailEditText.setText(oldCompany.getEmail());
        productsEditText.setText(oldCompany.getProducts());

        if (oldCompany.getType() == CompanyType.Consultoria) {
            radioGroupCompanyType.check(R.id.radio_type_consultoria);

        } else if (oldCompany.getType() == CompanyType.DesarrolloALaMedida) {
            radioGroupCompanyType.check(R.id.radio_type_desarrollo);

        } else if (oldCompany.getType() == CompanyType.FabricaDeSoftware) {
            radioGroupCompanyType.check(R.id.radio_type_fabrica);
        }
    }
}