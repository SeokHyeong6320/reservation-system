package com.project.reservation.customer.service.impl;

import com.project.reservation.customer.repository.ReviewRepository;
import com.project.reservation.customer.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
}
