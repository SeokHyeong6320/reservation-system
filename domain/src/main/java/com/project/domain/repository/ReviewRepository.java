package com.project.domain.repository;

import com.project.domain.entity.Review;
import com.project.storeservice.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Override
    @EntityGraph(attributePaths = {"customer", "store"})
    Optional<Review> findById(Long id);

    List<Review> findByStore(Store store);

    Long countByStore(Store store);

}
