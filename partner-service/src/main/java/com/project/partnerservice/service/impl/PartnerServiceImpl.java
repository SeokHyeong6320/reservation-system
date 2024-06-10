package com.project.partnerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.UserDto;
import com.project.domain.entity.User;
import com.project.domain.repository.UserRepository;
import com.project.domain.type.UserType;
import com.project.partnerservice.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.PARTNER_ALREADY_ENROLLED;
import static com.project.common.exception.ErrorCode.USER_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;

    // 파트너 가입 서비스
    @Override
    public UserDto enrollPartner(String partnerEmail) {

        User findUser = userRepository
                .findByEmail(partnerEmail).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 이미 파트너인 경우 에러 발생
        checkIsPartner(findUser);

        // userType을 PARTNER로 변경
        findUser.enrollPartner();

        // dto로 변환해서 반환
        return UserDto.fromEntity(findUser);
    }

    private void checkIsPartner(User findUser) {
        if (findUser.getUserType() == UserType.PARTNER) {
            throw new CustomException(PARTNER_ALREADY_ENROLLED);
        }
    }


}
