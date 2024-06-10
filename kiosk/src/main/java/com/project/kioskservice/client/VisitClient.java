package com.project.kioskservice.client;

import com.project.domain.model.VisitDomainForm;
import com.project.domain.response.VisitResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "visit-service", url = "localhost:8080")
public interface VisitClient {

    @PostMapping("/reservation/visit")
    ResponseEntity<VisitResponse> visitReservation(@RequestBody VisitDomainForm form);
}
