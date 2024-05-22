package com.project.reservation.store.model;


import com.project.reservation.store.entity.StoreStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreForm {

    @Size(max = 20)
    private String name;
    @Size(max = 100)
    private String description;
    @NotBlank
    private Address address;
    @NotBlank
    private StoreStatus status;


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
