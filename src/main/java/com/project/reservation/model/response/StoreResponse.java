package com.project.reservation.model.response;

import com.project.reservation.dto.StoreDto;
import lombok.*;

public class StoreResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class AddStoreResponse {

        private final Long ownerId;
        private final String storeName;
        private final String description;

        private final Address address;

        public static AddStoreResponse fromDto(StoreDto storeDto) {
            return AddStoreResponse.builder()
                    .ownerId(storeDto.getOwner().getId())
                    .storeName(storeDto.getName())
                    .description(storeDto.getDescription())
                    .address(Address.fromDto(storeDto))
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class StoreInfoResponse {

        private final String storeName;
        private final String description;

        private final Double star;
        private final Boolean isAvail;

        private final Address address;


        public static StoreInfoResponse fromDto(StoreDto storeDto) {
            return StoreInfoResponse.builder()
                    .storeName(storeDto.getName())
                    .description(storeDto.getDescription())
                    .star(storeDto.getStar())
                    .isAvail(storeDto.getIsAvail())
                    .address(Address.fromDto(storeDto))
                    .build();
        }

    }




    @Data
    @AllArgsConstructor
    @Builder
    public static class Address {
        private final String detailAddress;
        private final String zipcode;

        public static Address fromDto(StoreDto storeDto) {
            return Address.builder()
                    .detailAddress(storeDto.getDetailAddress())
                    .zipcode(storeDto.getZipcode())
                    .build();
        }
    }
}
