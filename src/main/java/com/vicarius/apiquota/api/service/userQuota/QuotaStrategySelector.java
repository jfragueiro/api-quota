package com.vicarius.apiquota.api.service.userQuota;

import com.vicarius.apiquota.api.service.DataAccessStrategySelector;
import com.vicarius.apiquota.api.service.userQuota.elastic.QuotaServiceStrategyElasticImpl;
import com.vicarius.apiquota.api.service.userQuota.mysql.QuotaServiceStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static com.vicarius.apiquota.common.Util.isDaytime;

@Component
public class QuotaStrategySelector implements DataAccessStrategySelector<QuotaServiceStrategy> {
    private final QuotaServiceStrategyImpl quotaServiceStrategy;
    private final QuotaServiceStrategyElasticImpl quotaServiceStrategyElastic;

    @Autowired
    public QuotaStrategySelector(QuotaServiceStrategyImpl quotaServiceStrategy, QuotaServiceStrategyElasticImpl quotaServiceStrategyElastic) {
        this.quotaServiceStrategy = quotaServiceStrategy;
        this.quotaServiceStrategyElastic = quotaServiceStrategyElastic;
    }

    @Override
    public QuotaServiceStrategy selectDataAccessStrategy() {
        if (isDaytime()) {
            return quotaServiceStrategy;
        } else {
            return quotaServiceStrategyElastic;
        }
    }
}