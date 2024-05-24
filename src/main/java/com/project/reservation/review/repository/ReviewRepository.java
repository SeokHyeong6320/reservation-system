package com.project.reservation.review.repository;

import com.project.reservation.review.entity.Review;
import com.project.reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Long countByStore(Store store);

}
