package com.project.storeservice.client;

import com.project.domain.dto.KioskAddDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kiosk-service", url = "localhost:8081")
public interface KioskClient {

    @PostMapping("/kiosk/add")
    ResponseEntity<Long> addKiosk(@RequestBody KioskAddDto kioskAddDto);
}
