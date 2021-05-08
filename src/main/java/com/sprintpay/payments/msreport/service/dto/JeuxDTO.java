package com.sprintpay.payments.msreport.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.payments.msreport.domain.Jeux} entity.
 */
public class JeuxDTO implements Serializable {
    
    private Long id;

    private String nom;

    private String description;

    private LocalDate dateCreation;

    private String concepteur;

    private Double prix;

    private Integer meilleurScore;

    private String lienTelechargement;

    private String lienJouer;


    private Long competitionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getConcepteur() {
        return concepteur;
    }

    public void setConcepteur(String concepteur) {
        this.concepteur = concepteur;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getMeilleurScore() {
        return meilleurScore;
    }

    public void setMeilleurScore(Integer meilleurScore) {
        this.meilleurScore = meilleurScore;
    }

    public String getLienTelechargement() {
        return lienTelechargement;
    }

    public void setLienTelechargement(String lienTelechargement) {
        this.lienTelechargement = lienTelechargement;
    }

    public String getLienJouer() {
        return lienJouer;
    }

    public void setLienJouer(String lienJouer) {
        this.lienJouer = lienJouer;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JeuxDTO)) {
            return false;
        }

        return id != null && id.equals(((JeuxDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JeuxDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", concepteur='" + getConcepteur() + "'" +
            ", prix=" + getPrix() +
            ", meilleurScore=" + getMeilleurScore() +
            ", lienTelechargement='" + getLienTelechargement() + "'" +
            ", lienJouer='" + getLienJouer() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
