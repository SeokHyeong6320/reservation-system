package com.project.reservation.partner.model;

import com.project.reservation.auth.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PartnerResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PartnerEnrollResponse {
        private final String email;
        private final String userType;

        public static PartnerEnrollResponse of (UserDto userDto) {
            return PartnerEnrollResponse.builder()
                    .email(userDto.getEmail())
                    .userType(userDto.getUserType().name())
                    .build();
        }
    }
}
