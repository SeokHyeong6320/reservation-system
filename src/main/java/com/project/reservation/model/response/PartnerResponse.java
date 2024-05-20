package com.project.reservation.model.response;

import com.project.reservation.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PartnerResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Enroll {
        private final String email;
        private final String userType;

        public static PartnerResponse.Enroll of (UserDto userDto) {
            return Enroll.builder()
                    .email(userDto.getEmail())
                    .userType(userDto.getUserType().name())
                    .build();
        }
    }
}
