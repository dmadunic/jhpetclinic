package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.OwnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {


    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "removePets", ignore = true)
    Owner toEntity(OwnerDTO ownerDTO);

    default Owner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setId(id);
        return owner;
    }
}
