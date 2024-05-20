package com.project.reservation.model.input;


import lombok.*;

public class StoreForm {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AddStoreForm {

        private String name;
        private String description;
        private Address address;

    }

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
