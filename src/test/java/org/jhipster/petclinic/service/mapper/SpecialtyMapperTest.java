package org.jhipster.petclinic.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SpecialtyMapperTest {

    private SpecialtyMapper specialtyMapper;

    @BeforeEach
    public void setUp() {
        specialtyMapper = new SpecialtyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(specialtyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(specialtyMapper.fromId(null)).isNull();
    }
}
