package com.sprintpay.payments.msreport.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.payments.msreport.domain.Competition} entity.
 */
public class CompetitionDTO implements Serializable {
    
    private Long id;

    private String nom;

    private String description;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Integer nombreParticipant;

    
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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombreParticipant() {
        return nombreParticipant;
    }

    public void setNombreParticipant(Integer nombreParticipant) {
        this.nombreParticipant = nombreParticipant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitionDTO)) {
            return false;
        }

        return id != null && id.equals(((CompetitionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nombreParticipant=" + getNombreParticipant() +
            "}";
    }
}
