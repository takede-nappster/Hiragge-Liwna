package com.sprintpay.payments.msreport.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sprintpay.payments.msreport.domain.enumeration.UserType;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @ManyToMany
    @JoinTable(name = "utilisateur_match",
               joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "match_id", referencedColumnName = "id"))
    private Set<Match> matches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public Utilisateur userType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Utilisateur matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Utilisateur addMatch(Match match) {
        this.matches.add(match);
        match.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removeMatch(Match match) {
        this.matches.remove(match);
        match.getUtilisateurs().remove(this);
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
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
