package com.project.kioskservice.entity;

import com.project.reservation.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Kiosk {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiosk_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
