package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.VetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vet} and its DTO {@link VetDTO}.
 */
@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class})
public interface VetMapper extends EntityMapper<VetDTO, Vet> {


    @Mapping(target = "removeSpecialties", ignore = true)

    default Vet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vet vet = new Vet();
        vet.setId(id);
        return vet;
    }
}
