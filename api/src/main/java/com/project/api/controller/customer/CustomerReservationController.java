package com.project.api.controller.customer;

import com.project.common.model.SuccessResponse;
import com.project.customerservice.model.CustomerReservationForm;
import com.project.customerservice.service.CustomerReservationService;
import com.project.domain.response.ReservationResponse;
import com.project.securityservice.util.AuthVerityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final AuthVerityUtil authVerityUtil;

    private final CustomerReservationService reservationService;

    /**
     * 고객 예약 엔드포인트
     */
    @PostMapping("/{customerEmail}/reservation")
    public ResponseEntity<?> doReservation(
            @PathVariable String customerEmail,
            @RequestBody @Validated CustomerReservationForm form
            ) {

        // 올바른 고객의 접근인지 확인
        authVerityUtil.verifyUser(customerEmail);

        ReservationResponse reservationResponse =
                reservationService.makeReservation(customerEmail, form);

        return ResponseEntity.status(CREATED)
                .body(SuccessResponse.of(reservationResponse));
    }

}
