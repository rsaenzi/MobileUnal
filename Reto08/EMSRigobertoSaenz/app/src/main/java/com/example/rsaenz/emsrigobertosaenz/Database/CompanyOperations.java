package com.example.rsaenz.emsrigobertosaenz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;
import java.util.ArrayList;
import java.util.List;

public class CompanyOperations {

    public static final String LOGTAG = "COMPANIES_APP";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            CompanyDBHandler.COLUMN_ID,
            CompanyDBHandler.COLUMN_FIRST_NAME,
            CompanyDBHandler.COLUMN_LAST_NAME,
            CompanyDBHandler.COLUMN_GENDER,
            CompanyDBHandler.COLUMN_HIRE_DATE,
            CompanyDBHandler.COLUMN_DEPT
    };

    public CompanyOperations(Context context){
        dbhandler = new CompanyDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Company addCompany(Company Company){
        ContentValues values  = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_FIRST_NAME, Company.getFirstname());
        values.put(CompanyDBHandler.COLUMN_LAST_NAME, Company.getLastname());
        values.put(CompanyDBHandler.COLUMN_GENDER, Company.getGender());
        values.put(CompanyDBHandler.COLUMN_HIRE_DATE, Company.getHiredate());
        values.put(CompanyDBHandler.COLUMN_DEPT, Company.getDept());
        long insertid = database.insert(CompanyDBHandler.TABLE_COMPANIES,null,values);
        Company.setCompanyId(insertid);
        return Company;
    }

    // Getting single Company
    public Company getCompany(long id) {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES,allColumns,CompanyDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Company e = new Company(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return Company
        return e;
    }

    public List<Company> getAllCompanies() {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES,allColumns,null,null,null, null, null);

        List<Company> companies = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Company company = new Company();
                company.setCompanyId(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                company.setFirstname(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_FIRST_NAME)));
                company.setLastname(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_LAST_NAME)));
                company.setGender(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_GENDER)));
                company.setHiredate(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_HIRE_DATE)));
                company.setDept(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_DEPT)));
                companies.add(company);
            }
        }
        // return All Companies
        return companies;
    }

    // Updating Company
    public int updateCompany(Company company) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_FIRST_NAME, company.getFirstname());
        values.put(CompanyDBHandler.COLUMN_LAST_NAME, company.getLastname());
        values.put(CompanyDBHandler.COLUMN_GENDER, company.getGender());
        values.put(CompanyDBHandler.COLUMN_HIRE_DATE, company.getHiredate());
        values.put(CompanyDBHandler.COLUMN_DEPT, company.getDept());

        // updating row
        return database.update(CompanyDBHandler.TABLE_COMPANIES, values,
                CompanyDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(company.getCompanyId())});
    }

    // Deleting Company
    public void removeCompany(Company company) {

        database.delete(CompanyDBHandler.TABLE_COMPANIES, CompanyDBHandler.COLUMN_ID + "=" + company.getCompanyId(), null);
    }
}