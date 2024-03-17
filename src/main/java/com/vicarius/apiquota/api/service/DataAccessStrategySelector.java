package com.vicarius.apiquota.api.service;

public interface DataAccessStrategySelector<T> {
    T selectDataAccessStrategy();
}