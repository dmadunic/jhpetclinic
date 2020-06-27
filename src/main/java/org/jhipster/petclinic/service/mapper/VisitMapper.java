package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.VisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Visit} and its DTO {@link VisitDTO}.
 */
@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "pet.name", target = "petName")
    VisitDTO toDto(Visit visit);

    @Mapping(source = "petId", target = "pet")
    Visit toEntity(VisitDTO visitDTO);

    default Visit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Visit visit = new Visit();
        visit.setId(id);
        return visit;
    }
}
