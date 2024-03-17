package com.vicarius.apiquota.api.service.userQuota;

import com.vicarius.apiquota.model.UserQuota;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotaService {
    private final QuotaStrategySelector quotaStrategySelector;

    public QuotaService(QuotaStrategySelector quotaStrategySelector) {
        this.quotaStrategySelector = quotaStrategySelector;
    }

    public ResponseEntity<String> consumeQuota(String userId) {
        QuotaServiceStrategy quotaServiceStrategy = quotaStrategySelector.selectDataAccessStrategy();
        return quotaServiceStrategy.consumeQuota(userId);
    }

    public List<UserQuota> getUsersQuota() {
        QuotaServiceStrategy quotaServiceStrategy = quotaStrategySelector.selectDataAccessStrategy();
        return quotaServiceStrategy.getUsersQuota();
    }
}