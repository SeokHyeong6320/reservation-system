package com.project.reservation.service;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.model.input.StoreForm;

public interface StoreService {

    StoreDto addStore(Long id, StoreForm.AddStoreForm form);
}
