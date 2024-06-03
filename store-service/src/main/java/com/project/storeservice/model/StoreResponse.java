package com.project.storeservice.model;

import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.entity.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class StoreResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class AddStoreResponse {

        private final Long ownerId;
        private final String storeName;
        private final String description;

        private final StoreStatus status;

        private final Address address;

        public static AddStoreResponse fromDto(StoreDto storeDto) {
            return AddStoreResponse.builder()
                    .ownerId(storeDto.getOwner().getId())
                    .storeName(storeDto.getName())
                    .description(storeDto.getDescription())
                    .status(storeDto.getStatus())
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

        private final String star;

        private final StoreStatus status;

        private final Address address;


        public static StoreInfoResponse fromDto(StoreDto storeDto) {
            return StoreInfoResponse.builder()
                    .storeName(storeDto.getName())
                    .description(storeDto.getDescription())
                    // 별점은 소수점 첫째자리까지만 표시
                    .star(String.format("%.1f", storeDto.getStar()))
                    .status(storeDto.getStatus())
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
