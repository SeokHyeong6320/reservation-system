package com.project.reservation.dto;

import com.project.reservation.entity.*;
import com.project.reservation.entity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {

    private Long id;

    private User owner;

    private List<Reservation> reservations = new ArrayList<>();

    private String name;

    private String description;

    private Double star;

    private List<Review> reviews = new ArrayList<>();

    private String detailAddress;
    private String zipcode;
    private Double latitude;
    private Double longitude;

    private Boolean isAvail;




    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .owner(store.getOwner())
                .reservations(store.getReservations())
                .name(store.getName())
                .description(store.getDescription())
                .star(store.getStar())
                .reviews(store.getReviews())
                .detailAddress(store.getAddress().getDetailAddress())
                .zipcode(store.getAddress().getZipcode())
                .latitude(store.getAddress().getLatitude())
                .longitude(store.getAddress().getLongitude())
                .isAvail(store.getIsAvail())
                .build();
    }


}
