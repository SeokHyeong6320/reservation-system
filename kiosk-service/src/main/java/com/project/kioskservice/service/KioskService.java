package com.project.kioskservice.service;


import com.project.domain.dto.KioskAddDto;
import com.project.kioskservice.model.VisitForm;

public interface KioskService {

    Long addKiosk(KioskAddDto kioskAddDto);

    String visitStore(VisitForm form);

}
