package com.project.partnerservice.service;


import com.project.domain.dto.StoreDto;
import com.project.storeservice.model.StoreForm;

public interface PartnerStoreService {

    StoreDto addStore(Long id, StoreForm form);

    StoreDto updateStore(Long id, Long storeId, StoreForm form);

    void deleteStore(Long id, Long storeId);

}
