package com.vicarius.apiquota.api.service.impl.user;


import com.vicarius.apiquota.model.VicariusUser;

import java.util.List;
import java.util.Optional;

public interface UserServiceStrategy {
    public List<VicariusUser> getAllUsers();

    public Optional<VicariusUser> getUserById(String id);

    public VicariusUser createUser(VicariusUser user);

    public VicariusUser updateUser(String id, VicariusUser user);

    public void deleteUser(String id);

}
