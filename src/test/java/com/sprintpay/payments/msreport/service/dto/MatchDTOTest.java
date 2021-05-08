package com.sprintpay.payments.msreport.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sprintpay.payments.msreport.web.rest.TestUtil;

public class MatchDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchDTO.class);
        MatchDTO matchDTO1 = new MatchDTO();
        matchDTO1.setId(1L);
        MatchDTO matchDTO2 = new MatchDTO();
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO2.setId(matchDTO1.getId());
        assertThat(matchDTO1).isEqualTo(matchDTO2);
        matchDTO2.setId(2L);
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
        matchDTO1.setId(null);
        assertThat(matchDTO1).isNotEqualTo(matchDTO2);
    }
}
