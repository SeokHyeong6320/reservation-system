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

    @Data
    @AllArgsConstructor
    @Builder
    public static class Address {
        private final String detailAddress;
        private final String zipcode;
        private final Double latitude;
        private Double longitude;

        public static Address fromDto(StoreDto storeDto) {
            return Address.builder()
                    .detailAddress(storeDto.getDetailAddress())
                    .zipcode(storeDto.getZipcode())
                    .latitude(storeDto.getLatitude())
                    .longitude(storeDto.getLongitude())
                    .build();
        }
    }
}
