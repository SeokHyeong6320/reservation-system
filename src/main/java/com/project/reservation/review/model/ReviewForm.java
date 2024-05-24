package com.project.reservation.review.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

public class ReviewForm {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateReviewForm {

        @NotNull
        private Long reservationId;

        @NotNull
        @Range(min = 0, max = 5)
        private int star;

        @NotBlank
        @Size(max = 30)
        private String title;
        @NotNull
        @Size(max = 100)
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateReviewForm {
        @NotBlank
        @Size(max = 30)
        private String title;

        @NotNull
        @Range(min = 0, max = 5)
        private int star;

        @NotNull
        @Size(max = 100)
        private String content;

    }

}
