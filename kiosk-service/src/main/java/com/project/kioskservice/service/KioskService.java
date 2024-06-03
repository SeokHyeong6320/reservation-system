package com.project.kioskservice.service;

import com.project.reservation.kiosk.model.VisitForm;
import com.project.reservation.store.entity.Store;

public interface KioskService {

    void addKiosk(Store store);

    String visitStore(VisitForm form);

}
