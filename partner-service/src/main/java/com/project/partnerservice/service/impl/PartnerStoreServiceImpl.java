package com.project.partnerservice.service.impl;

import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.entity.UserType;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.common.util.impl.GeoUtil;
import com.project.reservation.kiosk.service.KioskService;
import com.project.partnerservice.service.PartnerStoreService;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.reservation.repository.ReservationRepository;
import com.project.reservation.review.entity.Review;
import com.project.reservation.review.repository.ReviewRepository;
import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.entity.Store;
import com.project.reservation.store.model.StoreForm;
import com.project.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.project.reservation.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PartnerStoreServiceImpl implements PartnerStoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    private final KioskService kioskService;


    // 상점 추가 기능
    @Override
    @Transactional
    public StoreDto addStore(Long id, StoreForm form) {

        // 해당 유저가 없으면 에러 발생
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 파트너가 아닌 경우 에러 발생
        validateIsPartner(findUser);

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(
                form.getAddress().getLatitude(), form.getAddress().getLongitude()
        );

        Store newStore = Store.fromForm(form, findUser);
        Store savedStore = storeRepository.save(newStore);

        // 점주의 가게 리스트에 추가
        findUser.getStoreList().add(savedStore);

        // 키오스크 등록
        kioskService.addKiosk(savedStore);

        // dto로 변환해서 반환
        return StoreDto.fromEntity(savedStore);
    }

    // 상점 정보 수정

    @Override
    @Transactional
    public StoreDto updateStore(Long id, Long storeId, StoreForm form) {

        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 올바른 소유자의 상점인지 확인
        validateStoreOwner(id, findStore);

        findStore.updateStore(form);

        return StoreDto.fromEntity(findStore);
    }


    // 상점 삭제

    @Override
    public void deleteStore(Long id, Long storeId) {

        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 올바른 소유자의 상점인지 확인
        validateStoreOwner(id, findStore);

        // 관련된 예약 모두 삭제
        List<Reservation> reservations = findStore.getReservations();
        reservationRepository.deleteAll(reservations);

        // 관련된 리뷰 모두 삭제
        List<Review> reviews = reviewRepository.findByStore(findStore);
        reviewRepository.deleteAll(reviews);

        storeRepository.delete(findStore);
    }

    // 파트너인지 확인
    private void validateIsPartner(User findUser) {
        if (findUser.getUserType() != UserType.PARTNER) {
            throw new CustomException(PARTNER_NOT_ENROLLED);
        }
    }

    // 상점 주인 아니면 에러 발생
    private void validateStoreOwner(Long ownerId, Store store) {
        if (!Objects.equals(ownerId, store.getOwner().getId())) {
            throw new CustomException(STORE_OWNER_NOT_MATCH);
        }
    }

}
