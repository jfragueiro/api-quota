package com.vicarius.apiquota.api.service.userQuota;


import com.vicarius.apiquota.model.UserQuota;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuotaServiceStrategy {

    ResponseEntity<String> consumeQuota(String userId);
    List<UserQuota> getUsersQuota();

}
