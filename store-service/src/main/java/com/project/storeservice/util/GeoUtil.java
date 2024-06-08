package com.project.storeservice.util;

import com.project.domain.entity.Store;

import java.util.List;

public interface GeoUtil {

    List<Store> getSortedByLocation(Double lat, Double lon);
}
