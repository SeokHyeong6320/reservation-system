package com.project.partnerservice.model;


import com.project.domain.model.StoreDomainForm;
import com.project.domain.type.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfoForm {

    @Size(max = 20)
    private String name;
    @Size(max = 100)
    private String description;

    private Address address;
    @NotNull
    private StoreStatus status;

    public StoreDomainForm toDomainForm() {
        return StoreDomainForm.builder()
                .name(this.name)
                .description(this.description)
                .address(
                        StoreDomainForm.Address.builder()
                                .detailAddress(address.detailAddress)
                                .zipcode(address.zipcode)
                                .latitude(address.latitude)
                                .longitude(address.longitude)
                                .build()
                )
                .status(this.status)
                .build();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address {
        @NotBlank
        @Size(max = 100)
        private String detailAddress;
        @NotBlank
        @Pattern(regexp = "^\\d{5}$")   // 우편번호 정규식
        private String zipcode;
        @NotBlank
        private Double latitude;
        @NotBlank
        private Double longitude;
    }

}
