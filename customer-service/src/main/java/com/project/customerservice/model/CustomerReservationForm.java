package com.project.customerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.domain.model.ReservationDomainForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerReservationForm {

    @NotNull
    private Long storeId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reserveDt;

    @NotBlank
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    private String contact;

    public ReservationDomainForm toDomainForm() {
        return ReservationDomainForm.builder()
                .reserveDt(reserveDt)
                .contact(contact)
                .build();
    }

}
