package com.project.domain.entity;

import com.project.domain.model.StoreDomainForm;
import com.project.domain.type.StoreStatus;
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
//
//    @Column(name = "owner_id")
//    private Long ownerId;
//
    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    @Column(name = "store_name")
    private String name;

    @Column(name = "store_des")
    private String description;

    @Column(name = "store_star")
    private Double star;

//    @OneToMany(mappedBy = "store")
//    private List<Review> reviews = new ArrayList<>();   // 별점 계산 위한 필드

    @Embedded
    private Address address;        // 주소값은 임베디드 타입으로 정의

    @Column(name = "store_avail")
    @Enumerated(value = EnumType.STRING)
    private StoreStatus status;


    public static Store fromForm(StoreDomainForm form, User owner) {
        return Store.builder()
                .owner(owner)
                .name(form.getName())
                .description(form.getDescription())
                .star(0.0)
                .address(Address.fromForm(form))
                .status(form.getStatus())
                .build();
    }

    public void updateStore(StoreDomainForm form) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.status = form.getStatus();
        this.address.updateAddress(form.getAddress());
    }

    // 현재 예약 가능한 상태인지 확인
    public boolean isAvail() {
        return status == StoreStatus.AVAILABLE;
    }

    public void calculateStar(int newStar, long count) {
        double oldStar = this.star;

        this.star = (oldStar * count + newStar) / (count + 1);
    }

}
