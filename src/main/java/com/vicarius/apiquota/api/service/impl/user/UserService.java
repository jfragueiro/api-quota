package com.vicarius.apiquota.api.service.impl.user;

import com.vicarius.apiquota.model.VicariusUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserStrategySelector userStrategySelector;

    public UserService(UserStrategySelector userStrategySelector) {
        this.userStrategySelector = userStrategySelector;
    }

    public List<VicariusUser> getAllUsers() {
        UserServiceStrategy userServiceStrategy = userStrategySelector.selectDataAccessStrategy();
        return userServiceStrategy.getAllUsers();
    }

    public Optional<VicariusUser> getUserById(String id) {
        UserServiceStrategy userServiceStrategy = userStrategySelector.selectDataAccessStrategy();
        return userServiceStrategy.getUserById(id);
    }

    public VicariusUser createUser(VicariusUser user) {
        UserServiceStrategy userServiceStrategy = userStrategySelector.selectDataAccessStrategy();
        return userServiceStrategy.createUser(user);
    }

    public VicariusUser updateUser(String id, VicariusUser updatedUser) {
        UserServiceStrategy userServiceStrategy = userStrategySelector.selectDataAccessStrategy();
        return userServiceStrategy.updateUser(id, updatedUser);
    }

    public void deleteUser(String id) {
        UserServiceStrategy userServiceStrategy = userStrategySelector.selectDataAccessStrategy();
        userServiceStrategy.deleteUser(id);
    }
}