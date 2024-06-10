package com.project.partnerservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.StoreDto;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.repository.StoreRepository;
import com.project.domain.repository.UserRepository;
import com.project.domain.type.UserType;
import com.project.partnerservice.model.StoreInfoForm;
import com.project.partnerservice.service.PartnerStoreService;
import com.project.storeservice.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerStoreServiceImpl implements PartnerStoreService {

    private final StoreManagementService storeManagementService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;


    /**
     * 상점 등록
     */
    @Override
    public StoreDto addStore(String partnerEmail, StoreInfoForm form) {

        // 해당 유저가 없으면 에러 발생
        User findUser = userRepository.findByEmail(partnerEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 파트너가 아닌 경우 에러 발생
        validateIsPartner(findUser);

        // store-service로 넘겨서 상점 등록
        return storeManagementService.addStore(findUser, form.toDomainForm());
    }

    /**
     * 상점 정보 수정
     */
    @Override
    public StoreDto updateStore(String partnerEmail, Long storeId, StoreInfoForm form) {

        // 해당 상점이 없으면 에러 발생
        Store findStore = findStoreById(storeId);

        // 올바른 소유자의 상점인지 확인
        validateStoreOwner(partnerEmail, findStore);

        return storeManagementService.updateStore(findStore, form.toDomainForm());
    }

    /**
     * 상점 삭제
     */
    @Override
    public void deleteStore(String partnerEmail, Long storeId) {

        // 해당 상점이 없으면 에러 발생
        Store findStore = findStoreById(storeId);

        // 올바른 소유자의 상점인지 확인
        validateStoreOwner(partnerEmail, findStore);

        storeManagementService.deleteStore(findStore);

    }

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }


    // 파트너인지 확인
    private void validateIsPartner(User findUser) {
        if (findUser.getUserType() != UserType.PARTNER) {
            throw new CustomException(PARTNER_NOT_ENROLLED);
        }
    }

    // 상점 주인 아니면 에러 발생
    private void validateStoreOwner(String partnerEmail, Store store) {
        if (!Objects.equals(partnerEmail, store.getOwner().getEmail())) {
            throw new CustomException(STORE_OWNER_NOT_MATCH);
        }
    }
}
