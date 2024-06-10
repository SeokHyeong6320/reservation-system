package com.project.kioskservice.model;

import com.project.domain.model.VisitDomainForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private Long kioskId;

    @NotNull
    private Long reservationId;

    @NotBlank
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    private String contact;

    @NotBlank
    @Size(min = 8, max = 8)
    private String code;

    public VisitDomainForm toDomainForm() {
        return VisitDomainForm.builder()
                .kioskId(this.kioskId)
                .reservationId(this.reservationId)
                .contact(this.contact)
                .code(this.code)
                .build();
    }
}
