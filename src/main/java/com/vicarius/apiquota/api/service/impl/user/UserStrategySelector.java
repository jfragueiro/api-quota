package com.vicarius.apiquota.api.service.impl.user;

import com.vicarius.apiquota.api.service.impl.user.elastic.UserServiceStrategyElasticImpl;
import com.vicarius.apiquota.api.service.impl.user.mysql.UserServiceStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class UserStrategySelector implements DataAccessStrategy {
    private final UserServiceStrategyImpl userServiceStrategy;
    private final UserServiceStrategyElasticImpl userServiceStrategyElastic;

    @Autowired
    public UserStrategySelector(UserServiceStrategyImpl userServiceStrategy, UserServiceStrategyElasticImpl userServiceStrategyElastic) {
        this.userServiceStrategy = userServiceStrategy;
        this.userServiceStrategyElastic = userServiceStrategyElastic;
    }

    @Override
    public UserServiceStrategy selectDataAccessStrategy() {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(8, 59)) || currentTime.isAfter(LocalTime.of(17, 1))) {
            return userServiceStrategyElastic; // Use external API during nighttime
        } else {
            return userServiceStrategy; // Use JPA during daytime
        }
    }

}