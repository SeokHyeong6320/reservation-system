package com.project.reservation.store.entity;

import com.project.reservation.auth.entity.User;
import com.project.reservation.common.entity.BaseEntity;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.customer.entity.Review;
import com.project.reservation.store.model.StoreForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "STORE_INFO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    @Column(name = "store_name")
    private String name;

    @Column(name = "store_des")
    private String description;

    @Column(name = "store_star")
    private Double star;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();   // 별점 계산 위한 필드

    @Embedded
    private Address address;        // 주소값은 임베디드 타입으로 정의

    @Column(name = "store_avail")
    @Enumerated(value = EnumType.STRING)
    private StoreStatus status;


    public static Store fromForm(StoreForm form, User user) {
        return Store.builder()
                .owner(user)
                .name(form.getName())
                .description(form.getDescription())
                .star(0.0)
                .address(Address.fromForm(form))
                .status(form.getStatus())
                .build();
    }

    public void updateStore(StoreForm form) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.status = form.getStatus();
        this.address.updateAddress(form.getAddress());
    }

    // 현재 예약 가능한 상태인지 확인
    public boolean isAvail() {
        return status == StoreStatus.AVAILABLE;
    }

}
