package org.jhipster.petclinic.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhipster.petclinic.web.rest.TestUtil;

public class OwnerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerDTO.class);
        OwnerDTO ownerDTO1 = new OwnerDTO();
        ownerDTO1.setId(1L);
        OwnerDTO ownerDTO2 = new OwnerDTO();
        assertThat(ownerDTO1).isNotEqualTo(ownerDTO2);
        ownerDTO2.setId(ownerDTO1.getId());
        assertThat(ownerDTO1).isEqualTo(ownerDTO2);
        ownerDTO2.setId(2L);
        assertThat(ownerDTO1).isNotEqualTo(ownerDTO2);
        ownerDTO1.setId(null);
        assertThat(ownerDTO1).isNotEqualTo(ownerDTO2);
    }
}
