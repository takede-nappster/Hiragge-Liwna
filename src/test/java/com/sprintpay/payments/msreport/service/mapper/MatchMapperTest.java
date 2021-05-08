package com.sprintpay.payments.msreport.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchMapperTest {

    private MatchMapper matchMapper;

    @BeforeEach
    public void setUp() {
        matchMapper = new MatchMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(matchMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(matchMapper.fromId(null)).isNull();
    }
}
