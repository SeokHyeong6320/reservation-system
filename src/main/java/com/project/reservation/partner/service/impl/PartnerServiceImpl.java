package com.project.reservation.partner.service.impl;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.entity.UserType;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.partner.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.reservation.common.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;


    // 파트너 가입 서비스
    @Override
    public UserDto enrollPartner(Long id) {

        User findUser = userRepository
                .findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 이미 파트너인 경우 에러 발생
        if (findUser.getUserType() == UserType.PARTNER) {
            throw new CustomException(PARTNER_ALREADY_ENROLLED);
        }

        // userType을 PARTNER로 변경
        findUser.enrollPartner();

        // dto로 변환해서 반환
        return UserDto.fromEntity(findUser);
    }
}
