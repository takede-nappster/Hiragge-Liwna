package com.sprintpay.payments.msreport.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.sprintpay.payments.msreport.domain.Jeux} entity. This class is used
 * in {@link com.sprintpay.payments.msreport.web.rest.JeuxResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jeuxes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JeuxCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private LocalDateFilter dateCreation;

    private StringFilter concepteur;

    private DoubleFilter prix;

    private IntegerFilter meilleurScore;

    private StringFilter lienJouer;

    private StringFilter description;

    private LongFilter competitionId;

    public JeuxCriteria() {
    }

    public JeuxCriteria(JeuxCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.dateCreation = other.dateCreation == null ? null : other.dateCreation.copy();
        this.concepteur = other.concepteur == null ? null : other.concepteur.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
        this.meilleurScore = other.meilleurScore == null ? null : other.meilleurScore.copy();
        this.lienJouer = other.lienJouer == null ? null : other.lienJouer.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.competitionId = other.competitionId == null ? null : other.competitionId.copy();
    }

    @Override
    public JeuxCriteria copy() {
        return new JeuxCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LocalDateFilter getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateFilter dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StringFilter getConcepteur() {
        return concepteur;
    }

    public void setConcepteur(StringFilter concepteur) {
        this.concepteur = concepteur;
    }

    public DoubleFilter getPrix() {
        return prix;
    }

    public void setPrix(DoubleFilter prix) {
        this.prix = prix;
    }

    public IntegerFilter getMeilleurScore() {
        return meilleurScore;
    }

    public void setMeilleurScore(IntegerFilter meilleurScore) {
        this.meilleurScore = meilleurScore;
    }

    public StringFilter getLienJouer() {
        return lienJouer;
    }

    public void setLienJouer(StringFilter lienJouer) {
        this.lienJouer = lienJouer;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(LongFilter competitionId) {
        this.competitionId = competitionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JeuxCriteria that = (JeuxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(dateCreation, that.dateCreation) &&
            Objects.equals(concepteur, that.concepteur) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(meilleurScore, that.meilleurScore) &&
            Objects.equals(lienJouer, that.lienJouer) &&
            Objects.equals(description, that.description) &&
            Objects.equals(competitionId, that.competitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        dateCreation,
        concepteur,
        prix,
        meilleurScore,
        lienJouer,
        description,
        competitionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JeuxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (dateCreation != null ? "dateCreation=" + dateCreation + ", " : "") +
                (concepteur != null ? "concepteur=" + concepteur + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
                (meilleurScore != null ? "meilleurScore=" + meilleurScore + ", " : "") +
                (lienJouer != null ? "lienJouer=" + lienJouer + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (competitionId != null ? "competitionId=" + competitionId + ", " : "") +
            "}";
    }

}
