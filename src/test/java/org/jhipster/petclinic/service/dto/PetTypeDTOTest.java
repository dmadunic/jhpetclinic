package org.jhipster.petclinic.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhipster.petclinic.web.rest.TestUtil;

public class PetTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetTypeDTO.class);
        PetTypeDTO petTypeDTO1 = new PetTypeDTO();
        petTypeDTO1.setId(1L);
        PetTypeDTO petTypeDTO2 = new PetTypeDTO();
        assertThat(petTypeDTO1).isNotEqualTo(petTypeDTO2);
        petTypeDTO2.setId(petTypeDTO1.getId());
        assertThat(petTypeDTO1).isEqualTo(petTypeDTO2);
        petTypeDTO2.setId(2L);
        assertThat(petTypeDTO1).isNotEqualTo(petTypeDTO2);
        petTypeDTO1.setId(null);
        assertThat(petTypeDTO1).isNotEqualTo(petTypeDTO2);
    }
}
