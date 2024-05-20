package com.project.reservation.service.impl;

import com.project.reservation.repository.StoreRepository;
import com.project.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
}
