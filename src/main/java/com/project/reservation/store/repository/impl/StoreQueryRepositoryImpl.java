package com.project.reservation.store.repository.impl;

import com.project.reservation.store.entity.QStore;
import com.project.reservation.store.entity.Store;
import com.project.reservation.store.repository.StoreQueryRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreQueryRepositoryImpl implements StoreQueryRepository {

    // JPAQueryFactory는 QueryConfig에 Bean으로 등록
    private final JPAQueryFactory queryFactory;

    /**
     * 유저 위도, 경도 값 받아와서 거리 계산해서 오름차순 정렬
     */
    @Override
    public Page<Store> findSortByDistance(Pageable pageable, Double lat, Double lon) {

        QStore store = QStore.store;

        OrderSpecifier<Double> orderSpecifier =
                new OrderSpecifier<>(Order.ASC,
                Expressions.numberTemplate(
                        Double.class,
                        // 두 좌표 간 거리 구하는 공식
                        "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                        lat, store.address.latitude, lon, store.address.longitude));

        List<Store> resultList =
                queryFactory
                .selectFrom(store)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store);

        // Page<Store> 형태로 반환
        return PageableExecutionUtils.getPage(resultList, pageable, countQuery::fetchOne);
//       return new PageImpl<>(resultList, pageable, totalCount);
    }
}
