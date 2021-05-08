package com.sprintpay.payments.msreport.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.payments.msreport.web.rest.TestUtil;

public class JeuxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jeux.class);
        Jeux jeux1 = new Jeux();
        jeux1.setId(1L);
        Jeux jeux2 = new Jeux();
        jeux2.setId(jeux1.getId());
        assertThat(jeux1).isEqualTo(jeux2);
        jeux2.setId(2L);
        assertThat(jeux1).isNotEqualTo(jeux2);
        jeux1.setId(null);
        assertThat(jeux1).isNotEqualTo(jeux2);
    }
}
