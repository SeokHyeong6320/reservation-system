package com.project.reservation.common.util;

import com.project.reservation.common.exception.CustomException;
import com.project.reservation.common.exception.ErrorCode;

public class GeoUtil {

    /* 정상범위
         위도 (Latitude): 약 33도에서 39도 사이
         경도 (Longitude): 약 124도에서 132도 사이
     */
    public static void isValidLocation(Double lat, Double lon) {

        boolean isValidLat = lat >= 32 && lat <= 40;
        boolean isValidLon = lon >= 123 && lon <= 131;

        if (!isValidLat || !isValidLon) {
            throw new CustomException(ErrorCode.GPS_COORDINATE_INVALID);
        }
    }
}
