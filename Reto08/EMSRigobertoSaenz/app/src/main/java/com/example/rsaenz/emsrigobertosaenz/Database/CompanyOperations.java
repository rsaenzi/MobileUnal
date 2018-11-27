package com.example.rsaenz.emsrigobertosaenz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.rsaenz.emsrigobertosaenz.Entity.Employee;
import java.util.ArrayList;
import java.util.List;

public class CompanyOperations {
    public static final String LOGTAG = "EMP_MNGMNT_SYS";

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
    public Employee addEmployee(Employee Employee){
        ContentValues values  = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_FIRST_NAME,Employee.getFirstname());
        values.put(CompanyDBHandler.COLUMN_LAST_NAME,Employee.getLastname());
        values.put(CompanyDBHandler.COLUMN_GENDER, Employee.getGender());
        values.put(CompanyDBHandler.COLUMN_HIRE_DATE, Employee.getHiredate());
        values.put(CompanyDBHandler.COLUMN_DEPT, Employee.getDept());
        long insertid = database.insert(CompanyDBHandler.TABLE_EMPLOYEES,null,values);
        Employee.setEmpId(insertid);
        return Employee;
    }

    // Getting single Employee
    public Employee getEmployee(long id) {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_EMPLOYEES,allColumns,CompanyDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee e = new Employee(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return Employee
        return e;
    }

    public List<Employee> getAllEmployees() {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_EMPLOYEES,allColumns,null,null,null, null, null);

        List<Employee> employees = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Employee employee = new Employee();
                employee.setEmpId(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                employee.setFirstname(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_FIRST_NAME)));
                employee.setLastname(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_LAST_NAME)));
                employee.setGender(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_GENDER)));
                employee.setHiredate(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_HIRE_DATE)));
                employee.setDept(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_DEPT)));
                employees.add(employee);
            }
        }
        // return All Employees
        return employees;
    }

    // Updating Employee
    public int updateEmployee(Employee employee) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_FIRST_NAME, employee.getFirstname());
        values.put(CompanyDBHandler.COLUMN_LAST_NAME, employee.getLastname());
        values.put(CompanyDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(CompanyDBHandler.COLUMN_HIRE_DATE, employee.getHiredate());
        values.put(CompanyDBHandler.COLUMN_DEPT, employee.getDept());

        // updating row
        return database.update(CompanyDBHandler.TABLE_EMPLOYEES, values,
                CompanyDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(employee.getEmpId())});
    }

    // Deleting Employee
    public void removeEmployee(Employee employee) {

        database.delete(CompanyDBHandler.TABLE_EMPLOYEES, CompanyDBHandler.COLUMN_ID + "=" + employee.getEmpId(), null);
    }
}