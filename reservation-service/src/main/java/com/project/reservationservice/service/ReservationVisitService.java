package com.project.reservationservice.service;

import com.project.domain.dto.VisitDto;
import com.project.domain.model.VisitDomainForm;
import com.project.domain.response.VisitResponse;

public interface ReservationVisitService {

    VisitDto visit(VisitDomainForm form);
}
