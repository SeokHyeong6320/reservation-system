package com.project.partnerservice.service;

import com.project.domain.dto.StoreDto;
import com.project.partnerservice.model.StoreInfoForm;

public interface PartnerStoreService {

    StoreDto addStore(Long userId, StoreInfoForm form);

    StoreDto updateStore(Long userId, Long storeId, StoreInfoForm form);

    void deleteStore(Long userId, Long storeId);
}
