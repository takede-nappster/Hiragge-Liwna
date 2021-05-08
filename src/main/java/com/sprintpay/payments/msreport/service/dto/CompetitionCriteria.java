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
 * Criteria class for the {@link com.sprintpay.payments.msreport.domain.Competition} entity. This class is used
 * in {@link com.sprintpay.payments.msreport.web.rest.CompetitionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /competitions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompetitionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter description;

    private LocalDateFilter dateDebut;

    private LocalDateFilter dateFin;

    private IntegerFilter nombreParticipant;

    private LongFilter jeuxId;

    private LongFilter matchId;

    public CompetitionCriteria() {
    }

    public CompetitionCriteria(CompetitionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.dateDebut = other.dateDebut == null ? null : other.dateDebut.copy();
        this.dateFin = other.dateFin == null ? null : other.dateFin.copy();
        this.nombreParticipant = other.nombreParticipant == null ? null : other.nombreParticipant.copy();
        this.jeuxId = other.jeuxId == null ? null : other.jeuxId.copy();
        this.matchId = other.matchId == null ? null : other.matchId.copy();
    }

    @Override
    public CompetitionCriteria copy() {
        return new CompetitionCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateFilter dateFin) {
        this.dateFin = dateFin;
    }

    public IntegerFilter getNombreParticipant() {
        return nombreParticipant;
    }

    public void setNombreParticipant(IntegerFilter nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
    }

    public LongFilter getJeuxId() {
        return jeuxId;
    }

    public void setJeuxId(LongFilter jeuxId) {
        this.jeuxId = jeuxId;
    }

    public LongFilter getMatchId() {
        return matchId;
    }

    public void setMatchId(LongFilter matchId) {
        this.matchId = matchId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompetitionCriteria that = (CompetitionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(nombreParticipant, that.nombreParticipant) &&
            Objects.equals(jeuxId, that.jeuxId) &&
            Objects.equals(matchId, that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        description,
        dateDebut,
        dateFin,
        nombreParticipant,
        jeuxId,
        matchId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (nombreParticipant != null ? "nombreParticipant=" + nombreParticipant + ", " : "") +
                (jeuxId != null ? "jeuxId=" + jeuxId + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
            "}";
    }

}
