package com.project.storeservice.service.impl;

import com.project.common.util.GeoUtil;
import com.project.domain.dto.StoreDto;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.model.StoreDomainForm;
import com.project.domain.repository.ReservationRepository;
import com.project.domain.repository.ReviewRepository;
import com.project.domain.repository.StoreRepository;
import com.project.storeservice.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class StoreManagementServiceImpl implements StoreManagementService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    private final KioskService kioskService;


    // 상점 추가 기능
    @Override
    @Transactional
    public StoreDto addStore(User partner, StoreDomainForm form) {

//        // 해당 유저가 없으면 에러 발생
//        User findUser = userRepository.findById(id)
//                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
//
//        // 파트너가 아닌 경우 에러 발생
//        validateIsPartner(findUser);

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(
                form.getAddress().getLatitude(), form.getAddress().getLongitude()
        );

        Store newStore = Store.fromForm(form, partner);
        Store savedStore = storeRepository.save(newStore);

        // 점주의 가게 리스트에 추가
        partner.getStoreList().add(savedStore);

        // 키오스크 등록
        kioskService.addKiosk(savedStore);

        // dto로 변환해서 반환
        return StoreDto.fromEntity(savedStore);
    }

    // 상점 정보 수정
    @Override
    @Transactional
    public StoreDto updateStore(Store store, StoreDomainForm form) {

//        Store findStore = storeRepository.findById(storeId)
//                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
//
//        // 올바른 소유자의 상점인지 확인
//        validateStoreOwner(id, findStore);

        store.updateStore(form);

        return StoreDto.fromEntity(store);
    }


    // 상점 삭제

    @Override
    public void deleteStore(Store store) {

//        Store findStore = storeRepository.findById(storeId)
//                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

//        // 올바른 소유자의 상점인지 확인
//        validateStoreOwner(id, findStore);

        // 관련된 예약 모두 삭제
        reservationRepository.deleteAllByStore(store);

        // 관련된 리뷰 모두 삭제
        reviewRepository.deleteAllByStore(store);

        // 해당 상점 삭제
        storeRepository.delete(store);
    }

//    // 파트너인지 확인
//    private void validateIsPartner(User findUser) {
//        if (findUser.getUserType() != UserType.PARTNER) {
//            throw new CustomException(PARTNER_NOT_ENROLLED);
//        }
//    }

//    // 상점 주인 아니면 에러 발생
//    private void validateStoreOwner(Long ownerId, Store store) {
//        if (!Objects.equals(ownerId, store.getOwner().getId())) {
//            throw new CustomException(STORE_OWNER_NOT_MATCH);
//        }
//    }

}
