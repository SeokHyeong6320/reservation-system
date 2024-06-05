package com.project.kioskservice.service;


import com.project.domain.entity.Store;
import com.project.kioskservice.model.VisitForm;

public interface KioskService {

    void addKiosk(Store store);

    String visitStore(VisitForm form);

}
