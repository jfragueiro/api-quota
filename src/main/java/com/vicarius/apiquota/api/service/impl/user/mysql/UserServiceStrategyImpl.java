package com.vicarius.apiquota.api.service.impl.user.mysql;

import com.vicarius.apiquota.api.service.impl.user.UserServiceStrategy;
import com.vicarius.apiquota.common.component.UserMapper;
import com.vicarius.apiquota.model.VicariusUser;
import com.vicarius.apiquota.persistence.entity.VicariusUserEntity;
import com.vicarius.apiquota.persistence.entity.UserQuotaEntity;
import com.vicarius.apiquota.persistence.repository.UserQuotaRepository;
import com.vicarius.apiquota.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceStrategyImpl implements UserServiceStrategy {
    private final UserRepository userRepository;
    private final UserQuotaRepository userQuotaRepository;
    private final UserMapper userMapper;

    public UserServiceStrategyImpl(UserRepository userRepository, UserQuotaRepository userQuotaRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userQuotaRepository = userQuotaRepository;
        this.userMapper = userMapper;
    }


    public List<VicariusUser> getAllUsers() {
        List<VicariusUserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::mapToUser)
                .collect(Collectors.toList());
    }

    public Optional<VicariusUser> getUserById(String id) {

        return userRepository.findById(id).map(userMapper::mapToUser);
    }

    public VicariusUser createUser(VicariusUser user) {

        user.setId(UUID.randomUUID().toString());
        VicariusUserEntity vicariusUserEntity = userMapper.mapToEntity(user);

        VicariusUserEntity savedVicariusUserEntity = userRepository.save(vicariusUserEntity);
        userQuotaRepository.save(UserQuotaEntity.builder()
                .id(UUID.randomUUID().toString())
                .blocked(false)
                .remainQuota(5) //TODO: this could be gotten from the DB, for this small propose is hardcoded to 5
                .userId(savedVicariusUserEntity.getId())
                .build());
        return userMapper.mapToUser(savedVicariusUserEntity);
    }

    public VicariusUser updateUser(String id, VicariusUser updatedUser) {
        if (userRepository.existsById(id)) {
            VicariusUserEntity existingVicariusUserEntity = userRepository.findById(id).orElseThrow();
            existingVicariusUserEntity.setFirstName(updatedUser.getFirstName());
            existingVicariusUserEntity.setLastName(updatedUser.getLastName());
            existingVicariusUserEntity.setLastLoginTimeUtc(updatedUser.getLastLoginTimeUtc());
            // Save the updated user entity
            VicariusUserEntity updatedVicariusUserEntity = userRepository.save(existingVicariusUserEntity);
            // Map the updated user entity back to a User object
            return userMapper.mapToUser(updatedVicariusUserEntity);
        } else {
            throw new RuntimeException("User with id " + id + " does not exist");
        }
    }

    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + " does not exist");
        }
    }
}
