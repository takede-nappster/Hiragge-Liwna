package com.sprintpay.payments.msreport.service.mapper;


import com.sprintpay.payments.msreport.domain.*;
import com.sprintpay.payments.msreport.service.dto.JeuxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jeux} and its DTO {@link JeuxDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface JeuxMapper extends EntityMapper<JeuxDTO, Jeux> {

    @Mapping(source = "competition.id", target = "competitionId")
    JeuxDTO toDto(Jeux jeux);

    @Mapping(source = "competitionId", target = "competition")
    Jeux toEntity(JeuxDTO jeuxDTO);

    default Jeux fromId(Long id) {
        if (id == null) {
            return null;
        }
        Jeux jeux = new Jeux();
        jeux.setId(id);
        return jeux;
    }
}
