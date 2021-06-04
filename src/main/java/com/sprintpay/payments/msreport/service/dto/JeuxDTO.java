package com.sprintpay.payments.msreport.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.sprintpay.payments.msreport.domain.Jeux} entity.
 */
public class JeuxDTO implements Serializable {
    
    private Long id;

    private String nom;

    private LocalDate dateCreation;

    private String concepteur;

    private Double prix;

    private Integer meilleurScore;

    private String lienJouer;

    @Lob
    private byte[] logo;

    private String logoContentType;
    @Lob
    private byte[] setupFile;

    private String setupFileContentType;
    @NotNull
    @Size(max = 10000)
    private String description;


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

    public String getLienJouer() {
        return lienJouer;
    }

    public void setLienJouer(String lienJouer) {
        this.lienJouer = lienJouer;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public byte[] getSetupFile() {
        return setupFile;
    }

    public void setSetupFile(byte[] setupFile) {
        this.setupFile = setupFile;
    }

    public String getSetupFileContentType() {
        return setupFileContentType;
    }

    public void setSetupFileContentType(String setupFileContentType) {
        this.setupFileContentType = setupFileContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            ", dateCreation='" + getDateCreation() + "'" +
            ", concepteur='" + getConcepteur() + "'" +
            ", prix=" + getPrix() +
            ", meilleurScore=" + getMeilleurScore() +
            ", lienJouer='" + getLienJouer() + "'" +
            ", logo='" + getLogo() + "'" +
            ", setupFile='" + getSetupFile() + "'" +
            ", description='" + getDescription() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
