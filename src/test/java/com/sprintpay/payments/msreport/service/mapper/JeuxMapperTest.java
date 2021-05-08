package com.sprintpay.payments.msreport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JeuxMapperTest {

    private JeuxMapper jeuxMapper;

    @BeforeEach
    public void setUp() {
        jeuxMapper = new JeuxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jeuxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jeuxMapper.fromId(null)).isNull();
    }
}
