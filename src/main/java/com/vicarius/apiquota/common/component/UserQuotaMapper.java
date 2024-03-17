package com.vicarius.apiquota.common.component;

import com.vicarius.apiquota.model.UserQuota;
import com.vicarius.apiquota.persistence.entity.UserQuotaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserQuotaMapper {

    public UserQuota mapToUserQuota(UserQuotaEntity entity) {
        return UserQuota.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .remainQuota(entity.getRemainQuota())
                .blocked(entity.getBlocked())
                .build();
    }
/*
    public UserQuotaEntity mapToEntity(UserQuota userQuota) {
        return UserQuotaEntity.builder()
                .id(userQuota.getId())
                .userId(userQuota.getUserId())
                .remainQuota(userQuota.getRemainQuota())
                .blocked(userQuota.getBlocked())
                .build();
    }*/
}