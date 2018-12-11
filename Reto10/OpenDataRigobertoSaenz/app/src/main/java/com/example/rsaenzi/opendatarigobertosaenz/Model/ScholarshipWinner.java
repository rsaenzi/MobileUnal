package com.example.rsaenzi.opendatarigobertosaenz.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScholarshipWinner {

    @SerializedName("c_digo_municipio_ies")
    @Expose
    private String cDigoMunicipioIes;
    @SerializedName("c_digo_municipio_residencia")
    @Expose
    private String cDigoMunicipioResidencia;
    @SerializedName("ciudad_ies")
    @Expose
    private String ciudadIes;
    @SerializedName("ciudad_residencia")
    @Expose
    private String ciudadResidencia;
    @SerializedName("convenio")
    @Expose
    private String convenio;
    @SerializedName("convocatoria")
    @Expose
    private String convocatoria;
    @SerializedName("depto_ies")
    @Expose
    private String deptoIes;
    @SerializedName("estado_de_la_condonaci_n")
    @Expose
    private String estadoDeLaCondonaciN;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tipo_de_educaci_n")
    @Expose
    private String tipoDeEducaciN;
    @SerializedName("valor_a_condonar")
    @Expose
    private String valorACondonar;

    public String getCDigoMunicipioIes() {
        return cDigoMunicipioIes;
    }

    public void setCDigoMunicipioIes(String cDigoMunicipioIes) {
        this.cDigoMunicipioIes = cDigoMunicipioIes;
    }

    public String getCDigoMunicipioResidencia() {
        return cDigoMunicipioResidencia;
    }

    public void setCDigoMunicipioResidencia(String cDigoMunicipioResidencia) {
        this.cDigoMunicipioResidencia = cDigoMunicipioResidencia;
    }

    public String getCiudadIes() {
        return ciudadIes;
    }

    public void setCiudadIes(String ciudadIes) {
        this.ciudadIes = ciudadIes;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDeptoIes() {
        return deptoIes;
    }

    public void setDeptoIes(String deptoIes) {
        this.deptoIes = deptoIes;
    }

    public String getEstadoDeLaCondonaciN() {
        return estadoDeLaCondonaciN;
    }

    public void setEstadoDeLaCondonaciN(String estadoDeLaCondonaciN) {
        this.estadoDeLaCondonaciN = estadoDeLaCondonaciN;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDeEducaciN() {
        return tipoDeEducaciN;
    }

    public void setTipoDeEducaciN(String tipoDeEducaciN) {
        this.tipoDeEducaciN = tipoDeEducaciN;
    }

    public String getValorACondonar() {
        return valorACondonar;
    }

    public void setValorACondonar(String valorACondonar) {
        this.valorACondonar = valorACondonar;
    }

    public String toString(){
        return "\n" +
                "Ciudad: " + getCiudadIes() + "\n" +
                "Convenio: " + getConvenio() + "\n" +
                "Convocatoria: " + getConvocatoria() + "\n" +
                "Estado: " + getEstadoDeLaCondonaciN() + "\n" +
                "Genero: " + getGenero() + "\n" +
                "Nivel: " + getTipoDeEducaciN() + "\n" +
                "Valor: " + getValorACondonar() + "\n";
    }
}