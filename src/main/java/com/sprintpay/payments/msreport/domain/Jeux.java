package com.sprintpay.payments.msreport.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Jeux.
 */
@Entity
@Table(name = "jeux")
public class Jeux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "concepteur")
    private String concepteur;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "meilleur_score")
    private Integer meilleurScore;

    @Column(name = "lien_jouer")
    private String lienJouer;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Lob
    @Column(name = "setup_file")
    private byte[] setupFile;

    @Column(name = "setup_file_content_type")
    private String setupFileContentType;

    @NotNull
    @Size(max = 10000)
    @Column(name = "description", length = 10000, nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "jeuxes", allowSetters = true)
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Jeux nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Jeux dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getConcepteur() {
        return concepteur;
    }

    public Jeux concepteur(String concepteur) {
        this.concepteur = concepteur;
        return this;
    }

    public void setConcepteur(String concepteur) {
        this.concepteur = concepteur;
    }

    public Double getPrix() {
        return prix;
    }

    public Jeux prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getMeilleurScore() {
        return meilleurScore;
    }

    public Jeux meilleurScore(Integer meilleurScore) {
        this.meilleurScore = meilleurScore;
        return this;
    }

    public void setMeilleurScore(Integer meilleurScore) {
        this.meilleurScore = meilleurScore;
    }

    public String getLienJouer() {
        return lienJouer;
    }

    public Jeux lienJouer(String lienJouer) {
        this.lienJouer = lienJouer;
        return this;
    }

    public void setLienJouer(String lienJouer) {
        this.lienJouer = lienJouer;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Jeux logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Jeux logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public byte[] getSetupFile() {
        return setupFile;
    }

    public Jeux setupFile(byte[] setupFile) {
        this.setupFile = setupFile;
        return this;
    }

    public void setSetupFile(byte[] setupFile) {
        this.setupFile = setupFile;
    }

    public String getSetupFileContentType() {
        return setupFileContentType;
    }

    public Jeux setupFileContentType(String setupFileContentType) {
        this.setupFileContentType = setupFileContentType;
        return this;
    }

    public void setSetupFileContentType(String setupFileContentType) {
        this.setupFileContentType = setupFileContentType;
    }

    public String getDescription() {
        return description;
    }

    public Jeux description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Jeux competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jeux)) {
            return false;
        }
        return id != null && id.equals(((Jeux) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jeux{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", concepteur='" + getConcepteur() + "'" +
            ", prix=" + getPrix() +
            ", meilleurScore=" + getMeilleurScore() +
            ", lienJouer='" + getLienJouer() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", setupFile='" + getSetupFile() + "'" +
            ", setupFileContentType='" + getSetupFileContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
