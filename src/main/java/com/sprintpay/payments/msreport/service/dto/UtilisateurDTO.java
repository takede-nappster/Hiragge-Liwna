package com.sprintpay.payments.msreport.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.sprintpay.payments.msreport.domain.enumeration.UserType;

/**
 * A DTO for the {@link com.sprintpay.payments.msreport.domain.Utilisateur} entity.
 */
public class UtilisateurDTO implements Serializable {
    
    private Long id;

    private UserType userType;

    private Set<MatchDTO> matches = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Set<MatchDTO> getMatches() {
        return matches;
    }

    public void setMatches(Set<MatchDTO> matches) {
        this.matches = matches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilisateurDTO)) {
            return false;
        }

        return id != null && id.equals(((UtilisateurDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UtilisateurDTO{" +
            "id=" + getId() +
            ", userType='" + getUserType() + "'" +
            ", matches='" + getMatches() + "'" +
            "}";
    }
}
