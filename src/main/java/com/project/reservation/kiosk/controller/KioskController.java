package com.project.reservation.kiosk.controller;

import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.kiosk.model.VisitForm;
import com.project.reservation.kiosk.model.VisitResponse;
import com.project.reservation.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
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
