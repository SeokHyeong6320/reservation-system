package com.project.storeservice.service;


import com.project.domain.dto.StoreDto;
import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.model.StoreDomainForm;

public interface StoreManagementService {

    StoreDto addStore(User partner, StoreDomainForm form);

    StoreDto updateStore(Store store, StoreDomainForm form);

    void deleteStore(Store store);

}
