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
 * Criteria class for the {@link com.sprintpay.payments.msreport.domain.Match} entity. This class is used
 * in {@link com.sprintpay.payments.msreport.web.rest.MatchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /matches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MatchCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateMatch;

    private LongFilter competitionId;

    private LongFilter utilisateurId;

    public MatchCriteria() {
    }

    public MatchCriteria(MatchCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateMatch = other.dateMatch == null ? null : other.dateMatch.copy();
        this.competitionId = other.competitionId == null ? null : other.competitionId.copy();
        this.utilisateurId = other.utilisateurId == null ? null : other.utilisateurId.copy();
    }

    @Override
    public MatchCriteria copy() {
        return new MatchCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDateFilter dateMatch) {
        this.dateMatch = dateMatch;
    }

    public LongFilter getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(LongFilter competitionId) {
        this.competitionId = competitionId;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MatchCriteria that = (MatchCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateMatch, that.dateMatch) &&
            Objects.equals(competitionId, that.competitionId) &&
            Objects.equals(utilisateurId, that.utilisateurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateMatch,
        competitionId,
        utilisateurId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateMatch != null ? "dateMatch=" + dateMatch + ", " : "") +
                (competitionId != null ? "competitionId=" + competitionId + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
            "}";
    }

}
