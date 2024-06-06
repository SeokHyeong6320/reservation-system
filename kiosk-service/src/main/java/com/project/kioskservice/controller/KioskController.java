package com.project.kioskservice.controller;

import com.project.common.model.SuccessResponse;
import com.project.kioskservice.model.VisitForm;
import com.project.kioskservice.model.VisitResponse;
import com.project.kioskservice.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class KioskController {

    private final KioskService kioskService;


    @PostMapping("/visit")
    public ResponseEntity<?> visitStore(
            @RequestBody @Validated VisitForm form
            ) {

        String customerName = kioskService.visitStore(form);

        return ResponseEntity.ok(
                SuccessResponse.of(VisitResponse.success(customerName))
        );
    }
}
