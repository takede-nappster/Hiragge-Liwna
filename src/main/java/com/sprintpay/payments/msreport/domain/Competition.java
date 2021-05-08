package com.sprintpay.payments.msreport.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "nombre_participant")
    private Integer nombreParticipant;

    @OneToMany(mappedBy = "competition")
    private Set<Jeux> jeuxes = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    private Set<Match> matches = new HashSet<>();

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

    public Competition nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Competition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Competition dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Competition dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombreParticipant() {
        return nombreParticipant;
    }

    public Competition nombreParticipant(Integer nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
        return this;
    }

    public void setNombreParticipant(Integer nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
    }

    public Set<Jeux> getJeuxes() {
        return jeuxes;
    }

    public Competition jeuxes(Set<Jeux> jeuxes) {
        this.jeuxes = jeuxes;
        return this;
    }

    public Competition addJeux(Jeux jeux) {
        this.jeuxes.add(jeux);
        jeux.setCompetition(this);
        return this;
    }

    public Competition removeJeux(Jeux jeux) {
        this.jeuxes.remove(jeux);
        jeux.setCompetition(null);
        return this;
    }

    public void setJeuxes(Set<Jeux> jeuxes) {
        this.jeuxes = jeuxes;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Competition matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Competition addMatch(Match match) {
        this.matches.add(match);
        match.setCompetition(this);
        return this;
    }

    public Competition removeMatch(Match match) {
        this.matches.remove(match);
        match.setCompetition(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nombreParticipant=" + getNombreParticipant() +
            "}";
    }
}
