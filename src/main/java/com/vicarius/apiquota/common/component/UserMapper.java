package com.vicarius.apiquota.common.component;

import com.vicarius.apiquota.model.VicariusUser;
import com.vicarius.apiquota.persistence.entity.VicariusUserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public VicariusUser mapToUser(VicariusUserEntity entity) {
        return VicariusUser.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .lastLoginTimeUtc(entity.getLastLoginTimeUtc())
                .build();
    }

    public VicariusUserEntity mapToEntity(VicariusUser user) {
        return VicariusUserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLoginTimeUtc(user.getLastLoginTimeUtc())
                .build();
    }
}