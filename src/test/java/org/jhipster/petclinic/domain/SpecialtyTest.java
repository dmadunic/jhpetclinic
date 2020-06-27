package org.jhipster.petclinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhipster.petclinic.web.rest.TestUtil;

public class SpecialtyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialty.class);
        Specialty specialty1 = new Specialty();
        specialty1.setId(1L);
        Specialty specialty2 = new Specialty();
        specialty2.setId(specialty1.getId());
        assertThat(specialty1).isEqualTo(specialty2);
        specialty2.setId(2L);
        assertThat(specialty1).isNotEqualTo(specialty2);
        specialty1.setId(null);
        assertThat(specialty1).isNotEqualTo(specialty2);
    }
}
