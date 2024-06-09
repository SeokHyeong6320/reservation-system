package com.project.kioskservice.service;


import com.project.domain.dto.KioskAddDto;
import com.project.domain.response.VisitResponse;
import com.project.kioskservice.model.VisitForm;

public interface KioskService {

    Long addKiosk(KioskAddDto kioskAddDto);

    VisitResponse visitStore(VisitForm form);

}
