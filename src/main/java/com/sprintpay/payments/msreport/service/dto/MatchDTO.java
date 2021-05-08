package com.sprintpay.payments.msreport.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.payments.msreport.domain.Match} entity.
 */
public class MatchDTO implements Serializable {
    
    private Long id;

    private LocalDate dateMatch;


    private Long competitionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDate dateMatch) {
        this.dateMatch = dateMatch;
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
        if (!(o instanceof MatchDTO)) {
            return false;
        }

        return id != null && id.equals(((MatchDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchDTO{" +
            "id=" + getId() +
            ", dateMatch='" + getDateMatch() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
