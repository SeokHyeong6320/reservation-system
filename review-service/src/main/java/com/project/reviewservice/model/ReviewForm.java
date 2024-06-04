package com.project.reviewservice.model;

import com.project.domain.model.ReviewDomainForm;
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


        public ReviewDomainForm.CreateReviewForm toDomainForm() {
            return ReviewDomainForm.CreateReviewForm
                    .builder()
                    .reservationId(this.reservationId)
                    .star(this.star)
                    .title(this.title)
                    .content(this.content)
                    .build();
        }

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

        public ReviewDomainForm.UpdateReviewForm toDomainForm() {
            return ReviewDomainForm.UpdateReviewForm
                    .builder()
                    .title(this.title)
                    .star(this.star)
                    .content(this.content)
                    .build();
        }
    }



}
