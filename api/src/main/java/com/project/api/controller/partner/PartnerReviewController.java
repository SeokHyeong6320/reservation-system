package com.project.api.controller.partner;

import com.project.common.model.SuccessResponse;
import com.project.partnerservice.service.PartnerReviewService;
import com.project.securityservice.util.AuthVerityUtil;
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
    private final AuthVerityUtil authVerityUtil;

    /**
     * 상점 주인이 본인 가게의 리뷰 삭제하는 엔드포인트
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable String partnerEmail,
            @PathVariable Long reviewId
    ) {

        // 올바른 유저의 접근인지 검증
        authVerityUtil.verifyUser(partnerEmail);

        partnerReviewService.deleteReview(partnerEmail, reviewId);

        return ResponseEntity.ok(
                SuccessResponse.of("deleteComplete")
        );
    }

}
