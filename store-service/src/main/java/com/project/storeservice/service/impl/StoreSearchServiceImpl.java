package com.project.storeservice.service.impl;

import com.project.common.util.GeoUtil;
import com.project.domain.dto.StoreDto;
import com.project.domain.model.UserLocation;
import com.project.domain.repository.StoreQueryRepository;
import com.project.domain.repository.StoreRepository;
import com.project.storeservice.service.StoreSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreSearchServiceImpl implements StoreSearchService {

    private final StoreRepository storeRepository;
    private final StoreQueryRepository storeQueryRepository;


    // 이름 순 정렬
    @Override
    public Page<StoreDto> sortByName(Pageable pageable) {

        Sort sort = Sort.by("name");
        PageRequest pageRequest = getPageRequest(pageable, sort);

        return toStoreDtoList(pageRequest);
    }

    // 별점 순 정렬
    @Override
    public Page<StoreDto> sortByStar(Pageable pageable) {

        // 별점 높은 순서 대로 정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "star");
        PageRequest pageRequest = getPageRequest(pageable, sort);

        return toStoreDtoList(pageRequest);
    }

    // 거리 순 정렬
    @Override
    public Page<StoreDto> sortByDistance(UserLocation userLocation, Pageable pageable) {

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(userLocation.getLat(), userLocation.getLon());

        // QueryDsl을 사용한 custom repository 구현
        return storeQueryRepository.findSortByDistance(
                        pageable, userLocation.getLat(), userLocation.getLon()
                )
                .map(StoreDto::fromEntity);
    }


    // StoreDto로 변환해 반환하는 코드 중복 제거
    private Page<StoreDto> toStoreDtoList(PageRequest pageRequest) {
        return storeRepository.findAll(pageRequest)
                .map(StoreDto::fromEntity);
    }


    // 페이지 적용
    private PageRequest getPageRequest(Pageable pageable, Sort sort) {
        return PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), sort
        );
    }

}




