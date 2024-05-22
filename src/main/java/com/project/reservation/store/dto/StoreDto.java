package com.project.reservation.store.dto;

import com.project.reservation.auth.entity.User;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.customer.entity.Review;
import com.project.reservation.store.entity.Store;
import com.project.reservation.store.entity.StoreStatus;
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

    private StoreStatus status;




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
                .status(store.getStatus())
                .build();
    }


}
