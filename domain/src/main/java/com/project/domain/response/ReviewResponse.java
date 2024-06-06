package com.project.domain.response;

import com.project.domain.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private final String title;
    private final int star;
    private final String content;

    private LocalDateTime regDt;
    private LocalDateTime updateDt;

    public static ReviewResponse fromDto(ReviewDto reviewDto) {
        return ReviewResponse.builder()
                .title(reviewDto.getTitle())
                .star(reviewDto.getStar())
                .content(reviewDto.getContent())
                .regDt(reviewDto.getRegDt())
                .updateDt(reviewDto.getUpdateDt())
                .build();
    }


}
