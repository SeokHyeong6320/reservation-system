package com.project.domain.model;


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
public class StoreDomainForm {

    private String name;
    private String description;

    private Address address;
    private StoreStatus status;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address {
        private String detailAddress;
        private String zipcode;
        private Double latitude;
        private Double longitude;
    }

}
