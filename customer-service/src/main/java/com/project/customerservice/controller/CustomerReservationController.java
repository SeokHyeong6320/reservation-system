package com.project.customerservice.controller;

import com.project.common.model.SuccessResponse;
import com.project.customerservice.service.CustomerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService reservationService;
    private final TokenValidator tokenValidator;


    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PARTNER')")
    @PostMapping("/customer/{id}/reservation")
    public ResponseEntity<?> doReservation(
            @PathVariable Long id,
            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated ReservationForm form
            ) {

        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto = reservationService.reservation(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }

}
