package com.project.storeservice.service.impl;

import com.project.common.util.impl.GeoUtil;
import com.project.reservationservice.repository.ReservationRepository;
import com.project.reviewservice.repository.ReviewRepository;
import com.project.domain.dto.StoreDto;
import com.project.domain.repository.StoreQueryRepository;
import com.project.domain.repository.StoreRepository;
import com.project.storeservice.service.StoreService;
import com.project.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final StoreQueryRepository storeQueryRepository;
    private final UserRepository userRepository;



    // 이름 순 정렬

    @Override
    @Transactional(readOnly = true)
    public Page<StoreDto> sortByName(Pageable pageable) {

        PageRequest pageRequest =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("name")
                );

        return toStoreDtoList(pageRequest);
    }
    // 별점 순 정렬
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDto> sortByStar(Pageable pageable) {

        // 별점 높은 순서 대로 정렬
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "star")
        );

        return toStoreDtoList(pageRequest);
    }

    // 거리 순 정렬
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable) {

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(lat, lon);

        // QueryDsl을 사용한 custom repository 구현
        return storeQueryRepository.findSortByDistance(pageable, lat, lon)
                .map(StoreDto::fromEntity);
    }





    // StoreDto로 변환해 반환하는 코드 중복 제거
    private Page<StoreDto> toStoreDtoList(PageRequest pageRequest) {
        return storeRepository.findAll(pageRequest)
                .map(StoreDto::fromEntity);
    }
}




