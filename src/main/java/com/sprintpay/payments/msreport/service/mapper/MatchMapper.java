package com.sprintpay.payments.msreport.service.mapper;


import com.sprintpay.payments.msreport.domain.*;
import com.sprintpay.payments.msreport.service.dto.MatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Match} and its DTO {@link MatchDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetitionMapper.class})
public interface MatchMapper extends EntityMapper<MatchDTO, Match> {

    @Mapping(source = "competition.id", target = "competitionId")
    MatchDTO toDto(Match match);

    @Mapping(source = "competitionId", target = "competition")
    @Mapping(target = "utilisateurs", ignore = true)
    @Mapping(target = "removeUtilisateur", ignore = true)
    Match toEntity(MatchDTO matchDTO);

    default Match fromId(Long id) {
        if (id == null) {
            return null;
        }
        Match match = new Match();
        match.setId(id);
        return match;
    }
}
