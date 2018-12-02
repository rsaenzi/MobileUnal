package com.example.rsaenz.emsrigobertosaenz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.rsaenz.emsrigobertosaenz.Entity.Company;
import com.example.rsaenz.emsrigobertosaenz.Entity.CompanyType;

import java.util.ArrayList;
import java.util.List;

public class CompanyOperations {

    public static final String LOGTAG = "COMPANIES_APP";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            CompanyDBHandler.COLUMN_ID,
            CompanyDBHandler.COLUMN_NAME,
            CompanyDBHandler.COLUMN_WEBSITE,
            CompanyDBHandler.COLUMN_PHONE,
            CompanyDBHandler.COLUMN_EMAIL,
            CompanyDBHandler.COLUMN_PRODUCTS,
            CompanyDBHandler.COLUMN_TYPE
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
        values.put(CompanyDBHandler.COLUMN_NAME, Company.getName());
        values.put(CompanyDBHandler.COLUMN_WEBSITE, Company.getWebsite());
        values.put(CompanyDBHandler.COLUMN_PHONE, Company.getPhone());
        values.put(CompanyDBHandler.COLUMN_EMAIL, Company.getEmail());
        values.put(CompanyDBHandler.COLUMN_PRODUCTS, Company.getProducts());
        values.put(CompanyDBHandler.COLUMN_TYPE, CompanyType.getFromType(Company.getType()));
        long insertid = database.insert(CompanyDBHandler.TABLE_COMPANIES,null,values);
        Company.setCompanyId(insertid);
        return Company;
    }

    // Getting single Company
    public Company getCompany(long id) {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES,allColumns,CompanyDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Company e = new Company(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), CompanyType.getFromString(cursor.getString(6)));
        // return Company
        return e;
    }

    public List<Company> getAllCompanies(CompanyType typeFilter) {

        String selectionFilter = null;

        if(typeFilter != null) {
            selectionFilter = CompanyDBHandler.COLUMN_TYPE+"=\"" + CompanyType.getFromType(typeFilter) + "\"";
        }

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES, allColumns, selectionFilter,null,null, null, null);

        List<Company> companies = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Company company = new Company();
                company.setCompanyId(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                company.setName(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_NAME)));
                company.setWebsite(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_WEBSITE)));
                company.setPhone(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_PHONE)));
                company.setEmail(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_EMAIL)));
                company.setProducts(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_PRODUCTS)));
                company.setType(CompanyType.getFromString(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TYPE))));
                companies.add(company);
            }
        }
        // return All Companies
        return companies;
    }

    // Updating Company
    public int updateCompany(Company company) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_NAME, company.getName());
        values.put(CompanyDBHandler.COLUMN_WEBSITE, company.getWebsite());
        values.put(CompanyDBHandler.COLUMN_PHONE, company.getPhone());
        values.put(CompanyDBHandler.COLUMN_EMAIL, company.getEmail());
        values.put(CompanyDBHandler.COLUMN_PRODUCTS, company.getProducts());
        values.put(CompanyDBHandler.COLUMN_TYPE, CompanyType.getFromType(company.getType()));

        // updating row
        return database.update(CompanyDBHandler.TABLE_COMPANIES, values,
                CompanyDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(company.getCompanyId())});
    }

    // Deleting Company
    public int removeCompany(Company company) {

        return database.delete(CompanyDBHandler.TABLE_COMPANIES, CompanyDBHandler.COLUMN_ID + "=" + company.getCompanyId(), null);
    }
}