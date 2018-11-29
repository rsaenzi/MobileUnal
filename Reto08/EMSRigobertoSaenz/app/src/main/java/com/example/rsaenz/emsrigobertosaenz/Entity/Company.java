package com.example.rsaenz.emsrigobertosaenz.Entity;

public class Company {

    private long companyId;
    private String name;
    private String website;
    private String phone;
    private String email;
    private String products;
    private CompanyType type;

    public Company(long id, String name, String website, String phone, String email, String products, CompanyType type){
        this.companyId = id;
        this.name = name;
        this.website = website;
        this.phone = phone;
        this.email = email;
        this.products = products;
        this.type = type;
    }

    public Company(){
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public String toString(){
        return "Company ID: " + getCompanyId() + "\n" +
                "Name: " + getName() + "\n" +
                "Website: " + getWebsite() + "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Products: " + getProducts() + "\n" +
                "Type: " + getType();
    }
}