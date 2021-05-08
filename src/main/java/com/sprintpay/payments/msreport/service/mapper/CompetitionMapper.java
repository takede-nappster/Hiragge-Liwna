package com.sprintpay.payments.msreport.service.mapper;


import com.sprintpay.payments.msreport.domain.*;
import com.sprintpay.payments.msreport.service.dto.CompetitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competition} and its DTO {@link CompetitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetitionMapper extends EntityMapper<CompetitionDTO, Competition> {


    @Mapping(target = "jeuxes", ignore = true)
    @Mapping(target = "removeJeux", ignore = true)
    @Mapping(target = "matches", ignore = true)
    @Mapping(target = "removeMatch", ignore = true)
    Competition toEntity(CompetitionDTO competitionDTO);

    default Competition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competition competition = new Competition();
        competition.setId(id);
        return competition;
    }
}
