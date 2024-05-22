package com.project.reservation.kiosk.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitForm {

    @NotNull
    private Long reservationId;

    @NotBlank
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    private String contact;

    @NotBlank
    @Size(min = 8, max = 8)
    private String code;
}
