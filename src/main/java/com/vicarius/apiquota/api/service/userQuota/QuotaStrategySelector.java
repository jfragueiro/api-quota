package com.vicarius.apiquota.api.service.userQuota;

import com.vicarius.apiquota.api.service.userQuota.elastic.QuotaServiceStrategyElasticImpl;
import com.vicarius.apiquota.api.service.userQuota.mysql.QuotaServiceStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class QuotaStrategySelector implements QuotaDataAccessStrategy {
    private final QuotaServiceStrategyImpl quotaServiceStrategy;
    private final QuotaServiceStrategyElasticImpl quotaServiceStrategyElastic;

    @Autowired
    public QuotaStrategySelector(QuotaServiceStrategyImpl quotaServiceStrategy, QuotaServiceStrategyElasticImpl quotaServiceStrategyElastic) {
        this.quotaServiceStrategy = quotaServiceStrategy;
        this.quotaServiceStrategyElastic = quotaServiceStrategyElastic;
    }

    @Override
    public QuotaServiceStrategy selectDataAccessStrategy() {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(8, 59)) || currentTime.isAfter(LocalTime.of(17, 1))) {
            return quotaServiceStrategyElastic; // Use JPA during day time
        } else {
            return quotaServiceStrategy; // Use Elastic during night time
        }
    }
}