package com.vicarius.apiquota.persistence.repository;

import com.vicarius.apiquota.persistence.entity.VicariusUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<VicariusUserEntity, String> {


}
