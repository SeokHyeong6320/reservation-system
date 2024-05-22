package com.project.reservation.customer.controller;

import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.model.ReservationForm;
import com.project.reservation.reservation.model.ReservationResponse;
import com.project.reservation.customer.service.CustomerReservationService;
import com.project.reservation.security.util.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.security.constant.SecurityConst.*;

@RestController
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService customerReservationService;
    private final TokenValidator tokenValidator;


    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PARTNER')")
    @PostMapping("/customer/{id}/reservation")
    public ResponseEntity<?> doReservation(
            @PathVariable Long id,
            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated ReservationForm form
            ) {

        tokenValidator.validateUser(id, header);

        ReservationDto reservationDto = customerReservationService.reservation(id, form);

        return ResponseEntity.ok(
                SuccessResponse.of(ReservationResponse.fromDto(reservationDto))
        );
    }

}
