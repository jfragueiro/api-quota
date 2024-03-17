package com.vicarius.apiquota.api.service.impl.user;

import com.vicarius.apiquota.api.service.impl.user.UserServiceStrategy;

public interface DataAccessStrategy {
    UserServiceStrategy selectDataAccessStrategy();
}
