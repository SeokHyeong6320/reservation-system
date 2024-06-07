package com.project.api.controller.customer;

import com.project.common.model.SuccessResponse;
import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.response.ReservationResponse;
import com.project.securityservice.util.TokenValidator;
import com.project.securityservice.util.impl.AuthVerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.securityservice.constant.SecurityConst.TOKEN_HEADER;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final AuthVerifyUtil authVerifyUtil;

    private final CustomerReservationService reservationService;
//    private final TokenValidator tokenValidator;


//    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'PARTNER')")
    @PostMapping("/{customerEmail}/reservation")
    public ResponseEntity<?> doReservation(
            @PathVariable String customerEmail,
//            @RequestHeader(TOKEN_HEADER) String header,
            @RequestBody @Validated CustomerReservationForm form
            ) {

//        tokenValidator.validateUser(id, header);

        authVerifyUtil.verifyUser(customerEmail);


        ReservationResponse reservationResponse =
                reservationService.makeReservation(customerEmail, form);

        return ResponseEntity.status(CREATED)
                .body(SuccessResponse.of(reservationResponse));
    }

}
