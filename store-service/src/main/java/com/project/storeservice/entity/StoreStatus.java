package com.project.storeservice.entity;

import lombok.Getter;

@Getter
public enum StoreStatus {

    AVAILABLE,      // 예약 가능
    UNAVAILABLE,    // 예약 불가
    FULL            // 만석

}
