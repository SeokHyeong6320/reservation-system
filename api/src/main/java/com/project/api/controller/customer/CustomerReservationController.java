package com.project.api.controller.customer;

import com.project.common.model.SuccessResponse;
import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService reservationService;
//    private final TokenValidator tokenValidator;


//    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PARTNER')")
    @PostMapping("/{id}/reservation")
    public ResponseEntity<?> doReservation(
            @PathVariable Long id,
//            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated CustomerReservationForm form
            ) {

//        tokenValidator.validateUser(id, header);


        ReservationResponse reservationResponse =
                reservationService.makeReservation(id, form);

        return ResponseEntity.ok(SuccessResponse.of(reservationResponse));
    }

}
