package com.project.domain.repository;

import com.project.domain.entity.Reservation;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    void deleteAllByStore(Store store);


    @Query("select r from Reservation r " +
            "join r.store s " +
            "join s.owner o" +
            " where " +
            "o = :owner and " +
            "function('YEAR', r.reserveDt) = :year and " +
            "function('MONTH', r.reserveDt) = :month and " +
            "function('DAY', r.reserveDt) = :day")
    @EntityGraph(attributePaths = {"customer", "store", "store.owner"})
    List<Reservation> findAllByTime(
            User owner,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );


    @Query("select o.id " +
            " from Reservation r  " +
            " join r.store s " +
            " join s.owner o" +
            " where r.id = :reservationId")
//    @EntityGraph(attributePaths = {"store", "store.owner"})
    Long findStoreOwnerId(@Param("reservationId") Long reservationId);

}


// 예약 -> 가게 -> 점주

// 점주 -> 가게리스트