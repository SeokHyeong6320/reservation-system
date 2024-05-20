package com.project.reservation.service.impl;

import com.project.reservation.repository.ReviewRepository;
import com.project.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
}
