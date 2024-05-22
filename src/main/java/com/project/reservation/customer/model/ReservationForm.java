package com.project.reservation.customer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationForm {

    @NotBlank
    private Long storeId;

    @NotBlank
    private LocalDateTime reserveDt;

    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    private String contact;

}
