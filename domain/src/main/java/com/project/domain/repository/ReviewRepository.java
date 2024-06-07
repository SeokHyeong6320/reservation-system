package com.project.domain.repository;

import com.project.domain.entity.Review;
import com.project.domain.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Override
    Optional<Review> findById(Long id);

    List<Review> findByStore(Store store);

    Long countByStore(Store store);

    void deleteAllByStore(Store store);

}
