package com.project.reservation.reservation.repository;

import com.project.reservation.auth.entity.User;
import com.project.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Query("select r from Reservation r " +
//            "join fetch r.store s " +
//            "join fetch s.owner " +
            " where " +
            "r.store.owner = :owner and " +
            "function('YEAR', r.reserveDt) = :year and " +
            "function('MONTH', r.reserveDt) = :month and " +
            "function('DAY', r.reserveDt) = :day")
    @EntityGraph(attributePaths = {"store", "store.owner"})
    List<Reservation> findAllByTime(
            User owner,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );

}


// 예약 -> 가게 -> 점주

// 점주 -> 가게리스트