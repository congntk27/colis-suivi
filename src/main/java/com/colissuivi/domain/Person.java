package com.colissuivi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Person.
 */
@Document
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("nom")
    private String nom;

    @Field("pre_nom")
    private String preNom;

    @Field("phone")
    private String phone;

    @Field("adresse")
    private String adresse;

    public String getNom() {
        return nom;
    }

    public Person nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPreNom() {
        return preNom;
    }

    public Person preNom(String preNom) {
        this.preNom = preNom;
        return this;
    }

    public void setPreNom(String preNom) {
        this.preNom = preNom;
    }

    public String getPhone() {
        return phone;
    }

    public Person phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Person adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return "Person{" +
            ", nom='" + getNom() + "'" +
            ", preNom='" + getPreNom() + "'" +
            ", phone='" + getPhone() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
