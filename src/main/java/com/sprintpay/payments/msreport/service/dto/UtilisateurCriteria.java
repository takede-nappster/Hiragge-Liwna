package com.sprintpay.payments.msreport.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.sprintpay.payments.msreport.domain.enumeration.UserType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sprintpay.payments.msreport.domain.Utilisateur} entity. This class is used
 * in {@link com.sprintpay.payments.msreport.web.rest.UtilisateurResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /utilisateurs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UtilisateurCriteria implements Serializable, Criteria {
    /**
     * Class for filtering UserType
     */
    public static class UserTypeFilter extends Filter<UserType> {

        public UserTypeFilter() {
        }

        public UserTypeFilter(UserTypeFilter filter) {
            super(filter);
        }

        @Override
        public UserTypeFilter copy() {
            return new UserTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UserTypeFilter userType;

    private LongFilter matchId;

    public UtilisateurCriteria() {
    }

    public UtilisateurCriteria(UtilisateurCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.matchId = other.matchId == null ? null : other.matchId.copy();
    }

    @Override
    public UtilisateurCriteria copy() {
        return new UtilisateurCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UserTypeFilter getUserType() {
        return userType;
    }

    public void setUserType(UserTypeFilter userType) {
        this.userType = userType;
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
        final UtilisateurCriteria that = (UtilisateurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(matchId, that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userType,
        matchId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UtilisateurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userType != null ? "userType=" + userType + ", " : "") +
                (matchId != null ? "matchId=" + matchId + ", " : "") +
            "}";
    }

}
