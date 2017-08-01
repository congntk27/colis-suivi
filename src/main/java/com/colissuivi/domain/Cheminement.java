package com.colissuivi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Cheminement.
 */
@Document
public class Cheminement implements Serializable {

    private static final long serialVersionUID = 1L;


    @Field("date_arrive")
    private ZonedDateTime dateArrive;
    
    @Field("agence")
    private Agence agence;

    public ZonedDateTime getDateArrive() {
        return dateArrive;
    }

    public Cheminement dateArrive(ZonedDateTime dateArrive) {
        this.dateArrive = dateArrive;
        return this;
    }

    public void setDateArrive(ZonedDateTime dateArrive) {
        this.dateArrive = dateArrive;
    }

   

    public Agence getAgence() {
		return agence;
	}

	public void setAgence(Agence agence) {
		this.agence = agence;
	}

	@Override
    public String toString() {
        return "Cheminement{" +
            ", dateArrive='" + getDateArrive() + "'" +
            "}";
    }
}
