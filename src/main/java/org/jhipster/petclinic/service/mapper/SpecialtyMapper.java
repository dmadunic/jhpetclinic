package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.SpecialtyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialty} and its DTO {@link SpecialtyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialtyMapper extends EntityMapper<SpecialtyDTO, Specialty> {


    @Mapping(target = "vets", ignore = true)
    @Mapping(target = "removeVets", ignore = true)
    Specialty toEntity(SpecialtyDTO specialtyDTO);

    default Specialty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Specialty specialty = new Specialty();
        specialty.setId(id);
        return specialty;
    }
}
