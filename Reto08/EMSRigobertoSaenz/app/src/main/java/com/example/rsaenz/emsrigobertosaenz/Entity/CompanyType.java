package com.example.rsaenz.emsrigobertosaenz.Entity;

public enum CompanyType {
    Consultoria,
    DesarrolloALaMedida,
    FabricaDeSoftware;

    public static CompanyType getFromString(String type) {

        if(type.equals("Consultoria")) {
            return CompanyType.Consultoria;

        } else if(type.equals("DesarrolloALaMedida")) {
            return CompanyType.DesarrolloALaMedida;

        } else if(type.equals("FabricaDeSoftware")) {
            return CompanyType.FabricaDeSoftware;

        } else {
            return CompanyType.Consultoria;
        }
    }

    public static String getFromType(CompanyType type) {

        if(type == CompanyType.Consultoria) {
            return "Consultoria";

        } else if(type == CompanyType.DesarrolloALaMedida) {
            return "DesarrolloALaMedida";

        } else if(type == CompanyType.FabricaDeSoftware) {
            return "FabricaDeSoftware";

        } else {
            return "Consultoria";
        }
    }
}
