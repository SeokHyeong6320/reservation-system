package com.project.reservation.controller;

import com.project.reservation.dto.StoreDto;
import com.project.reservation.model.input.StoreForm;
import com.project.reservation.model.response.StoreResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.reservation.model.input.StoreForm.*;
import static com.project.reservation.model.response.StoreResponse.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {



}
