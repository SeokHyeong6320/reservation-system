package com.project.reservation.entity;

import com.project.reservation.model.input.StoreForm;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {

    @Column(name = "store_address")
    private String detailAddress;

    @Column(name = "store_zipcode")
    private String zipcode;


    // 거리순 상점 목록 구하기 위한 위도, 경도 값
    @Column(name = "store_lat")
    private Double latitude;

    @Column(name = "store_lon")
    private Double longitude;



    public static Address fromForm(StoreForm.AddStoreForm form) {

        StoreForm.Address address = form.getAddress();

        return Address.builder()
                .detailAddress(address.getDetailAddress())
                .zipcode(address.getZipcode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }
}
