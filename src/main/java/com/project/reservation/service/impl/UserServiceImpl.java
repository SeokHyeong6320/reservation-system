package com.project.reservation.service.impl;

import com.project.reservation.repository.UserRepository;
import com.project.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
}
