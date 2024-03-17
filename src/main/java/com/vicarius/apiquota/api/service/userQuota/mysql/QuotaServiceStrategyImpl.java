package com.vicarius.apiquota.api.service.userQuota.mysql;

import com.vicarius.apiquota.api.service.userQuota.QuotaServiceStrategy;
import com.vicarius.apiquota.common.component.UserQuotaMapper;
import com.vicarius.apiquota.exception.QuotaExceededException;
import com.vicarius.apiquota.model.UserQuota;
import com.vicarius.apiquota.persistence.entity.UserQuotaEntity;
import com.vicarius.apiquota.persistence.repository.UserQuotaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
//@AllArgsConstructor
public class QuotaServiceStrategyImpl implements QuotaServiceStrategy {
    private final UserQuotaRepository userQuotaRepository;
    private final UserQuotaMapper userQuotaMapper;

    public QuotaServiceStrategyImpl(UserQuotaRepository userQuotaRepository, UserQuotaMapper userQuotaMapper) {
        this.userQuotaRepository = userQuotaRepository;
        this.userQuotaMapper = userQuotaMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<String> consumeQuota(String userId) {
        try {
            UserQuotaEntity userQuotaEntity = userQuotaRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Quota not found with userID: " + userId));
            if (userQuotaEntity.getBlocked() || userQuotaEntity.getRemainQuota() == 0) {
                userQuotaEntity.setBlocked(true);
                userQuotaRepository.save(userQuotaEntity);
                throw new QuotaExceededException("User exceeded quota.");
            }

            userQuotaEntity.setRemainQuota(userQuotaEntity.getRemainQuota() - 1);
            userQuotaRepository.save(userQuotaEntity);

            return ResponseEntity.ok("Quota consumed successfully.");
        } catch (QuotaExceededException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("User exceeded quota.");
        }
    }

    @Override
    public List<UserQuota> getUsersQuota() {
        List<UserQuotaEntity> userQuotaEntityList = userQuotaRepository.findAll();
        return userQuotaEntityList.stream()
                .map(userQuotaMapper::mapToUserQuota)
                .collect(Collectors.toList());

    }
}
