package com.project.reservation.store.service.impl;

import com.project.reservation.auth.entity.User;
import com.project.reservation.auth.entity.UserType;
import com.project.reservation.auth.repository.UserRepository;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.common.util.impl.GeoUtil;
import com.project.reservation.store.dto.StoreDto;
import com.project.reservation.store.entity.Store;
import com.project.reservation.store.model.StoreForm;
import com.project.reservation.store.repository.StoreRepository;
import com.project.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.reservation.common.exception.ErrorCode.PARTNER_NOT_ENROLLED;
import static com.project.reservation.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 상점 추가 기능
    @Override
    public StoreDto addStore(Long id, StoreForm.AddStoreForm form) {

        // 해당 유저가 없으면 에러 발생
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 파트너가 아닌 경우 에러 발생
        if (findUser.getUserType() != UserType.PARTNER) {
            throw new CustomException(PARTNER_NOT_ENROLLED);
        }

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(
                form.getAddress().getLatitude(), form.getAddress().getLongitude()
        );

        Store newStore = Store.fromForm(form, findUser);
        Store savedStore = storeRepository.save(newStore);

        // 점주의 가게 리스트에 추가
        findUser.getStoreList().add(savedStore);

        // dto로 변환해서 반환
        return StoreDto.fromEntity(savedStore);
    }

    // 이름 순 정렬
    @Override
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
    public Page<StoreDto> sortByDistance(Double lat, Double lon, Pageable pageable) {

        // 위도, 경도 값 정상값인지 확인 (정상 값 아닐 경우 에러 발생)
        GeoUtil.isValidLocation(lat, lon);

        // QueryDsl을 사용한 custom repository 구현
        return storeRepository.findSortByDistance(pageable, lat, lon)
                .map(StoreDto::fromEntity);
    }


    // StoreDto로 변환해 반환하는 코드 중복 제거
    private Page<StoreDto> toStoreDtoList(PageRequest pageRequest) {
        return storeRepository.findAll(pageRequest)
                .map(StoreDto::fromEntity);
    }
}




