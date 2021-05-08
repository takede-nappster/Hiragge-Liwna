package com.sprintpay.payments.msreport.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_match")
    private LocalDate dateMatch;

    @ManyToOne
    @JsonIgnoreProperties(value = "matches", allowSetters = true)
    private Competition competition;

    @ManyToMany(mappedBy = "matches")
    @JsonIgnore
    private Set<Utilisateur> utilisateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateMatch() {
        return dateMatch;
    }

    public Match dateMatch(LocalDate dateMatch) {
        this.dateMatch = dateMatch;
        return this;
    }

    public void setDateMatch(LocalDate dateMatch) {
        this.dateMatch = dateMatch;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Match competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Match utilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public Match addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.getMatches().add(this);
        return this;
    }

    public Match removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.getMatches().remove(this);
        return this;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", dateMatch='" + getDateMatch() + "'" +
            "}";
    }
}
