package com.project.partnerservice.service;

import com.project.domain.dto.StoreDto;
import com.project.partnerservice.model.StoreInfoForm;

public interface PartnerStoreService {

    StoreDto addStore(String partnerEmail, StoreInfoForm form);

    StoreDto updateStore(String partnerEmail, Long storeId, StoreInfoForm form);

    void deleteStore(String partnerEmail, Long storeId);
}
