package com.colissuivi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

import com.colissuivi.domain.enumeration.LivraisonStatut;

/**
 * A Colis.
 */
@Document(collection = "colis")
public class Colis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("statut")
    private LivraisonStatut statut;
    
    @Field("expidateur")
    private Person expidateur;
    
    @Field("destinataire")
    private Person destinataire;
    
    @Field("cheminements")
    private Cheminement[] cheminements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LivraisonStatut getStatut() {
        return statut;
    }

    public Colis statut(LivraisonStatut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(LivraisonStatut statut) {
        this.statut = statut;
    }
    
    

    public Cheminement[] getCheminements() {
		return cheminements;
	}

	public void setCheminements(Cheminement[] cheminements) {
		this.cheminements = cheminements;
	}
	
	

	public Person getExpidateur() {
		return expidateur;
	}

	public void setExpidateur(Person expidateur) {
		this.expidateur = expidateur;
	}

	public Person getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(Person destinataire) {
		this.destinataire = destinataire;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Colis colis = (Colis) o;
        if (colis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), colis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Colis{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
