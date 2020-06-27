package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.PetTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetType} and its DTO {@link PetTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PetTypeMapper extends EntityMapper<PetTypeDTO, PetType> {



    default PetType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PetType petType = new PetType();
        petType.setId(id);
        return petType;
    }
}
