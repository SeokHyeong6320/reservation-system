package com.project.reservation.entity;

import com.project.reservation.entity.baseentity.BaseEntity;
import com.project.reservation.model.input.SignForm;
import com.project.reservation.model.input.StoreForm;
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
    private Integer star;

    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();   // 별점 계산 위한 필드

    @Embedded
    private Address address;        // 주소값은 임베디드 타입으로 정의

    @Column(name = "store_avail")
    private Boolean isAvail;


    public static Store fromForm(StoreForm.AddStoreForm form, User user) {
        return Store.builder()
                .owner(user)
                .name(form.getName())
                .description(form.getDescription())
                .star(0)
                .address(Address.fromForm(form))
                .isAvail(true)
                .build();
    }

}
