package com.project.api.controller.partner;

import com.project.common.model.SuccessResponse;
import com.project.partnerservice.service.PartnerReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/{partnerEmail}/review")
public class PartnerReviewController {

    private final PartnerReviewService partnerReviewService;

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable String partnerEmail,
            @PathVariable Long reviewId
//            @RequestHeader(TOKEN_HEADER) String header
    ) {

//        tokenValidator.validateUser(id, header);

        partnerReviewService.deleteReview(partnerEmail, reviewId);

        return ResponseEntity.ok(
                SuccessResponse.of("deleteComplete")
        );
    }

}
