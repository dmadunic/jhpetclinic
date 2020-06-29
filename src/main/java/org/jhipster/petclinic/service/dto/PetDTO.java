package org.jhipster.petclinic.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link org.jhipster.petclinic.domain.Pet} entity.
 */
public class PetDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private LocalDate birthDate;

    private Long typeId;

    private String typeName;

    private Long ownerId;

    private String ownerLastName;

    private Set<VisitDTO> visits = new HashSet<>();

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long petTypeId) {
        this.typeId = petTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String petTypeName) {
        this.typeName = petTypeName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public Set<VisitDTO> getVisits() {
        return visits;
    }

    public void setVisits(Set<VisitDTO> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetDTO)) {
            return false;
        }

        return id != null && id.equals(((PetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLastName='" + getOwnerLastName() + "'" +
            "}";
    }
}
