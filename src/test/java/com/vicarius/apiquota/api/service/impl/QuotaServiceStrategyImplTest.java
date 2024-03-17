package com.vicarius.apiquota.api.service.impl;

import com.vicarius.apiquota.api.service.userQuota.mysql.QuotaServiceStrategyImpl;
import com.vicarius.apiquota.persistence.entity.UserQuotaEntity;
import com.vicarius.apiquota.persistence.repository.UserQuotaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QuotaServiceStrategyImplTest {

    @Mock
    private UserQuotaRepository userQuotaRepository;

    @InjectMocks
    private QuotaServiceStrategyImpl quotaService;

    public QuotaServiceStrategyImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testConsumeQuota_SuccessfulConsumption() {
        UserQuotaEntity userQuotaEntity = new UserQuotaEntity();
        userQuotaEntity.setBlocked(false);
        userQuotaEntity.setRemainQuota(5);

        when(userQuotaRepository.findByUserId(anyString())).thenReturn(Optional.of(userQuotaEntity));

        ResponseEntity<String> response = quotaService.consumeQuota("userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quota consumed successfully.", response.getBody());
        assertEquals(4, userQuotaEntity.getRemainQuota());
        verify(userQuotaRepository, times(1)).save(userQuotaEntity);
    }

    @Test
    void testConsumeQuota_ExceededQuota() {
        UserQuotaEntity userQuotaEntity = new UserQuotaEntity();
        userQuotaEntity.setBlocked(false);
        userQuotaEntity.setRemainQuota(0);

        when(userQuotaRepository.findByUserId(anyString())).thenReturn(Optional.of(userQuotaEntity));

        ResponseEntity<String> response = quotaService.consumeQuota("userId");

        assertEquals(HttpStatus.LOCKED, response.getStatusCode());
        assertEquals("User exceeded quota.", response.getBody());
        assertEquals(0, userQuotaEntity.getRemainQuota());
        verify(userQuotaRepository).save(userQuotaEntity);
    }

    @Test
    void testConsumeQuota_QuotaNotFound() {
        when(userQuotaRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        ResponseEntity<String> response = quotaService.consumeQuota("userId");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Quota not found with userID: userId", response.getBody());
        verify(userQuotaRepository, never()).save(any());
    }
}