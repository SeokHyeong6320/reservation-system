package com.project.reservation.partner.service;

import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.model.StoreForm;

public interface PartnerStoreService {

    StoreDto addStore(Long id, StoreForm form);

    StoreDto updateStore(Long id, Long storeId, StoreForm form);

    void deleteStore(Long id, Long storeId);

}
