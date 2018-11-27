package com.example.rsaenz.emsrigobertosaenz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.rsaenz.emsrigobertosaenz.Database.CompanyOperations;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateCompany extends AppCompatActivity implements DatePickerFragment.DateDialogListener{

    private static final String EXTRA_EMP_ID = "com.rsaenzi.companyapp.companyId";
    private static final String EXTRA_ADD_UPDATE = "com.rsaenzi.companyapp.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private ImageView calendarImage;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton,femaleRadioButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText deptEditText;
    private EditText hireDateEditText;
    private Button addUpdateButton;
    private Company newCompany;
    private Company oldCompany;
    private String mode;
    private long empId;
    private CompanyOperations companyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_company);
        newCompany = new Company();
        oldCompany = new Company();
        firstNameEditText = (EditText)findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText)findViewById(R.id.edit_text_last_name);
        hireDateEditText = (EditText) findViewById(R.id.edit_text_hire_date);
        radioGroup = (RadioGroup) findViewById(R.id.radio_gender);
        maleRadioButton = (RadioButton) findViewById(R.id.radio_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.radio_female);
        calendarImage = (ImageView)findViewById(R.id.image_view_hire_date);
        deptEditText = (EditText)findViewById(R.id.edit_text_dept);
        addUpdateButton = (Button)findViewById(R.id.button_add_update_company);
        companyData = new CompanyOperations(this);
        companyData.open();


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){

            addUpdateButton.setText("Update Company");
            empId = getIntent().getLongExtra(EXTRA_EMP_ID,0);

            initializeCompany(empId);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radio_male) {
                    newCompany.setGender("M");
                    if(mode.equals("Update")){
                        oldCompany.setGender("M");
                    }
                } else if (checkedId == R.id.radio_female) {
                    newCompany.setGender("F");
                    if(mode.equals("Update")){
                        oldCompany.setGender("F");
                    }

                }
            }

        });

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });


        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    newCompany.setFirstname(firstNameEditText.getText().toString());
                    newCompany.setLastname(lastNameEditText.getText().toString());
                    newCompany.setHiredate(hireDateEditText.getText().toString());
                    newCompany.setDept(deptEditText.getText().toString());
                    companyData.addCompany(newCompany);
                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ newCompany.getFirstname() + "has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,MainActivity.class);
                    startActivity(i);
                }else {
                    oldCompany.setFirstname(firstNameEditText.getText().toString());
                    oldCompany.setLastname(lastNameEditText.getText().toString());
                    oldCompany.setHiredate(hireDateEditText.getText().toString());
                    oldCompany.setDept(deptEditText.getText().toString());
                    companyData.updateCompany(oldCompany);
                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ oldCompany.getFirstname() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,MainActivity.class);
                    startActivity(i);

                }


            }
        });


    }

    private void initializeCompany(long companyId) {
        oldCompany = companyData.getCompany(companyId);
        firstNameEditText.setText(oldCompany.getFirstname());
        lastNameEditText.setText(oldCompany.getLastname());
        hireDateEditText.setText(oldCompany.getHiredate());
        radioGroup.check(oldCompany.getGender().equals("M") ? R.id.radio_male : R.id.radio_female);
        deptEditText.setText(oldCompany.getDept());
    }


    @Override
    public void onFinishDialog(Date date) {
        hireDateEditText.setText(formatDate(date));

    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }

}