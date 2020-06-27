package org.jhipster.petclinic.service.mapper;


import org.jhipster.petclinic.domain.*;
import org.jhipster.petclinic.service.dto.PetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(componentModel = "spring", uses = {PetTypeMapper.class, OwnerMapper.class})
public interface PetMapper extends EntityMapper<PetDTO, Pet> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    PetDTO toDto(Pet pet);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "ownerId", target = "owner")
    Pet toEntity(PetDTO petDTO);

    default Pet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(id);
        return pet;
    }
}
