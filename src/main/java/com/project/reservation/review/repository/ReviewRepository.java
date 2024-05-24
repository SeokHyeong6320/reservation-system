package com.project.reservation.review.repository;

import com.project.reservation.review.entity.Review;
import com.project.reservation.store.entity.Store;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Override
    @EntityGraph(attributePaths = {"customer", "store"})
    Optional<Review> findById(Long id);

    Long countByStore(Store store);

}
