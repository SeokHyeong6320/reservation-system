package com.project.storeservice.util.impl;

import com.project.domain.entity.Store;
import com.project.domain.repository.StoreRepository;
import com.project.storeservice.constant.RedisConstant;
import com.project.storeservice.util.GeoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.storeservice.constant.RedisConstant.*;

@Service
@RequiredArgsConstructor
public class GeoUtilImpl implements GeoUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final StoreRepository storeRepository;


    @Override
    public List<Store> getSortedByLocation(Double lat, Double lon) {
        List<Store> storeList = storeRepository.findAll();

        saveShopToRedis(storeList);

//        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();

//        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
//                geoOperations.radius(
//                        SHOP_LOCATION,
//                        new Circle(new Point(lon, lat), new Distance(Double.MAX_VALUE))
//                );

//        List<String> storeIds = results.getContent().stream()
//                .map(GeoResult::getContent)
//                .map(RedisGeoCommands.GeoLocation::getName)
//                .toList();

        storeList.sort(Comparator.comparingDouble(shop ->
                calculateDistance(
                        lat, lon,
                        shop.getAddress().getLatitude(),
                        shop.getAddress().getLongitude()
                )));

        return storeList;
    }

    private void saveShopToRedis(List<Store> storeList) {
        storeList.forEach(store ->
                redisTemplate.opsForGeo().add(
                        SHOP_LOCATION,
                        new Point(
                                store.getAddress().getLatitude(),
                                store.getAddress().getLongitude()
                        ),
                        store.getId().toString()
                )
        );
    }

    private static final int EARTH_RADIUS = 6371; // 지구 반지름 (단위: km)

    // 두 지점 간의 거리 계산 (단위: km)
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
