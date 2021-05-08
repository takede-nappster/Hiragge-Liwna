package com.sprintpay.payments.msreport.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.payments.msreport.web.rest.TestUtil;

public class JeuxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JeuxDTO.class);
        JeuxDTO jeuxDTO1 = new JeuxDTO();
        jeuxDTO1.setId(1L);
        JeuxDTO jeuxDTO2 = new JeuxDTO();
        assertThat(jeuxDTO1).isNotEqualTo(jeuxDTO2);
        jeuxDTO2.setId(jeuxDTO1.getId());
        assertThat(jeuxDTO1).isEqualTo(jeuxDTO2);
        jeuxDTO2.setId(2L);
        assertThat(jeuxDTO1).isNotEqualTo(jeuxDTO2);
        jeuxDTO1.setId(null);
        assertThat(jeuxDTO1).isNotEqualTo(jeuxDTO2);
    }
}
