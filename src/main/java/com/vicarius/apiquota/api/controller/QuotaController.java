package com.vicarius.apiquota.api.controller;

import com.vicarius.apiquota.api.service.userQuota.QuotaService;
import com.vicarius.apiquota.event.producer.dto.UserConsumeQuotaEvent;
import com.vicarius.apiquota.event.producer.impl.UserConsumeQuotaEventImpl;
import com.vicarius.apiquota.model.UserQuota;
import com.vicarius.apiquota.model.VicariusUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/quotas")
public class QuotaController {

    private final UserConsumeQuotaEventImpl quotaProducer;
    private final QuotaService quotaService;

    public QuotaController(UserConsumeQuotaEventImpl quotaProducer, QuotaService quotaService) {
        this.quotaProducer = quotaProducer;
        this.quotaService = quotaService;
    }

    @PostMapping("/consumeQuota/{userId}")
    public ResponseEntity<HttpStatus> consumeQuota(@PathVariable String userId) {
        quotaProducer.sendEvent(UserConsumeQuotaEvent.builder().userId(userId).build());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    @Cacheable
    public List<UserQuota> getUsersQuota() {

        return quotaService.getUsersQuota();
    }

    @GetMapping("/remain/{userId}")
    public VicariusUser getUser(@PathVariable String userId) {
        return new VicariusUser();
    }
    

}
