package com.project.reservation.store.model;


import com.project.reservation.store.entity.StoreStatus;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreForm {

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
