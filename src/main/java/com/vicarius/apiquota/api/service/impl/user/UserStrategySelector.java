package com.vicarius.apiquota.api.service.impl.user;

import com.vicarius.apiquota.api.service.DataAccessStrategySelector;
import com.vicarius.apiquota.api.service.impl.user.elastic.UserServiceStrategyElasticImpl;
import com.vicarius.apiquota.api.service.impl.user.mysql.UserServiceStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static com.vicarius.apiquota.common.Util.isDaytime;

@Component
public class UserStrategySelector implements DataAccessStrategySelector<UserServiceStrategy> {
    private final UserServiceStrategyImpl userServiceStrategy;
    private final UserServiceStrategyElasticImpl userServiceStrategyElastic;

    @Autowired
    public UserStrategySelector(UserServiceStrategyImpl userServiceStrategy, UserServiceStrategyElasticImpl userServiceStrategyElastic) {
        this.userServiceStrategy = userServiceStrategy;
        this.userServiceStrategyElastic = userServiceStrategyElastic;
    }

    @Override
    public UserServiceStrategy selectDataAccessStrategy() {
        if (isDaytime()) {
            return userServiceStrategy;
        } else {
            return userServiceStrategyElastic;
        }
    }

}