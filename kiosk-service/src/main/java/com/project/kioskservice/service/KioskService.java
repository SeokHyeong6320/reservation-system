package com.project.kioskservice.service;


import com.project.kioskservice.model.VisitForm;
import com.project.domain.entity.Store;

public interface KioskService {

    void addKiosk(Store store);

    String visitStore(VisitForm form);

}
