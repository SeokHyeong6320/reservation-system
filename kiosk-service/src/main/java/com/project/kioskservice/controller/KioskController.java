package com.project.kioskservice.controller;

import com.project.common.model.SuccessResponse;
import com.project.domain.dto.KioskAddDto;
import com.project.kioskservice.model.VisitForm;
import com.project.domain.response.VisitResponse;
import com.project.kioskservice.service.KioskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {

    private final KioskService kioskService;

    /**
     * kiosk 등록 엔드포인트 (상점 등록하면 openFeign에 의해 호출
     *
     */
    @PostMapping("/add")
    public ResponseEntity<?> addKiosk(@RequestBody KioskAddDto kioskAddDto) {

        return ResponseEntity.ok(kioskService.addKiosk(kioskAddDto));
    }


    @PostMapping("/visit")
    public ResponseEntity<?> visitStore(
            @RequestBody @Validated VisitForm form
            ) {

        VisitResponse visitResponse = kioskService.visitStore(form);

        return ResponseEntity.ok(
                SuccessResponse.of(visitResponse)
        );
    }
}
