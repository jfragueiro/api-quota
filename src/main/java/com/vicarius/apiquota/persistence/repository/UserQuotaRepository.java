package com.vicarius.apiquota.persistence.repository;

import com.vicarius.apiquota.persistence.entity.UserQuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserQuotaRepository extends JpaRepository<UserQuotaEntity, UUID> {

public Optional<UserQuotaEntity> findByUserId(String userId);
}
