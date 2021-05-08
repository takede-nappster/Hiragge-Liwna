package com.sprintpay.payments.msreport.service.mapper;


import com.sprintpay.payments.msreport.domain.*;
import com.sprintpay.payments.msreport.service.dto.UtilisateurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Utilisateur} and its DTO {@link UtilisateurDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatchMapper.class})
public interface UtilisateurMapper extends EntityMapper<UtilisateurDTO, Utilisateur> {


    @Mapping(target = "removeMatch", ignore = true)

    default Utilisateur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        return utilisateur;
    }
}
