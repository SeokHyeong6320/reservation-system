package com.project.partnerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.reservation.common.exception.CustomException;
import com.project.reservation.common.exception.ErrorCode;
import com.project.reservation.reservation.dto.ReservationDto;
import com.project.reservation.reservation.entity.ReservationApproveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationTimeTable {

    /**
     * 날짜별 예약 리스트 응답
     * 현재날짜, 날짜별 총 예약 수, 시간별 타임테이블 반환
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {

        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDate date;
        private final Integer totalCount;
        private final TimeTable timeTable;

        public static Response fromDto(LocalDate date, List<ReservationDto> reservationDtoList) {
            return Response.builder()
                    .date(date)
                    .totalCount(reservationDtoList.size())
                    .timeTable(TimeTable.fromDtos(reservationDtoList))
                    .build();
        }


    }


    /**
     * 시간별 타임테이블
     * 해당 날짜에 해당하는 예약 리스트 받아와 시간별로 나눔
     */
    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)     // 리스트가 비어있을경우 응답값에서 제외
    public static class TimeTable {
        @JsonProperty("00:00 ~ 00:59")
        private final List<Reservation> zero = new ArrayList<>();
        @JsonProperty("01:00 ~ 01:59")
        private final List<Reservation> one = new ArrayList<>();
        @JsonProperty("02:00 ~ 02:59")
        private final List<Reservation> two = new ArrayList<>();
        @JsonProperty("03:00 ~ 03:59")
        private final List<Reservation> three = new ArrayList<>();
        @JsonProperty("04:00 ~ 04:59")
        private final List<Reservation> four = new ArrayList<>();
        @JsonProperty("05:00 ~ 05:59")
        private final List<Reservation> five = new ArrayList<>();
        @JsonProperty("06:00 ~ 06:59")
        private final List<Reservation> six = new ArrayList<>();
        @JsonProperty("07:00 ~ 07:59")
        private final List<Reservation> seven = new ArrayList<>();
        @JsonProperty("08:00 ~ 08:59")
        private final List<Reservation> eight = new ArrayList<>();
        @JsonProperty("09:00 ~ 09:59")
        private final List<Reservation> nine = new ArrayList<>();
        @JsonProperty("10:00 ~ 10:59")
        private final List<Reservation> ten = new ArrayList<>();
        @JsonProperty("11:00 ~ 11:59")
        private final List<Reservation> eleven = new ArrayList<>();
        @JsonProperty("12:00 ~ 12:59")
        private final List<Reservation> twelve = new ArrayList<>();
        @JsonProperty("13:00 ~ 13:59")
        private final List<Reservation> thirteen = new ArrayList<>();
        @JsonProperty("14:00 ~ 14:59")
        private final List<Reservation> fourteen = new ArrayList<>();
        @JsonProperty("15:00 ~ 15:59")
        private final List<Reservation> fifteen = new ArrayList<>();
        @JsonProperty("16:00 ~ 16:59")
        private final List<Reservation> sixteen = new ArrayList<>();
        @JsonProperty("17:00 ~ 17:59")
        private final List<Reservation> seventeen = new ArrayList<>();
        @JsonProperty("18:00 ~ 18:59")
        private final List<Reservation> eighteen = new ArrayList<>();
        @JsonProperty("19:00 ~ 19:59")
        private final List<Reservation> nineteen = new ArrayList<>();
        @JsonProperty("20:00 ~ 20:59")
        private final List<Reservation> twenty = new ArrayList<>();
        @JsonProperty("21:00 ~ 21:59")
        private final List<Reservation> twentyOne = new ArrayList<>();
        @JsonProperty("22:00 ~ 22:59")
        private final List<Reservation> twentyTwo = new ArrayList<>();
        @JsonProperty("23:00 ~ 23:59")
        private final List<Reservation> twentyThree = new ArrayList<>();


        public static TimeTable fromDtos(List<ReservationDto> reservationDtoList) {

            TimeTable timeTable = new TimeTable();

            reservationDtoList.forEach(reservationDto -> {

                Reservation reservation = Reservation.fromDto(reservationDto);

                try {
                    int hour = reservationDto.getReserveDt().getHour();

                    // if문 돌면서 시간별 리스트에 예약정보 넣음
                    if (hour == 0) {
                        timeTable.getZero().add(reservation);
                    } else if (hour == 1) {
                        timeTable.getOne().add(reservation);
                    } else if (hour == 2) {
                        timeTable.getTwo().add(reservation);
                    } else if (hour == 3) {
                        timeTable.getThree().add(reservation);
                    } else if (hour == 4) {
                        timeTable.getFour().add(reservation);
                    } else if (hour == 5) {
                        timeTable.getFive().add(reservation);
                    } else if (hour == 6) {
                        timeTable.getSix().add(reservation);
                    } else if (hour == 7) {
                        timeTable.getSeven().add(reservation);
                    } else if (hour == 8) {
                        timeTable.getEight().add(reservation);
                    } else if (hour == 9) {
                        timeTable.getNine().add(reservation);
                    } else if (hour == 10) {
                        timeTable.getTen().add(reservation);
                    } else if (hour == 11) {
                        timeTable.getEleven().add(reservation);
                    } else if (hour == 12) {
                        timeTable.getTwelve().add(reservation);
                    } else if (hour == 13) {
                        timeTable.getThirteen().add(reservation);
                    } else if (hour == 14) {
                        timeTable.getFourteen().add(reservation);
                    } else if (hour == 15) {
                        timeTable.getFifteen().add(reservation);
                    } else if (hour == 16) {
                        timeTable.getSixteen().add(reservation);
                    } else if (hour == 17) {
                        timeTable.getSeventeen().add(reservation);
                    } else if (hour == 18) {
                        timeTable.getEighteen().add(reservation);
                    } else if (hour == 19) {
                        timeTable.getNineteen().add(reservation);
                    } else if (hour == 20) {
                        timeTable.getTwenty().add(reservation);
                    } else if (hour == 21) {
                        timeTable.getTwentyOne().add(reservation);
                    } else if (hour == 22) {
                        timeTable.getTwentyTwo().add(reservation);
                    } else if (hour == 23) {
                        timeTable.getTwentyThree().add(reservation);
                    }

                } catch (Exception e) {
                    throw new CustomException(ErrorCode.RESERVATION_TIME_INVALID);
                }

            });



            return timeTable;
        }

    }


    /**
     * 예약 타임테이블에 보여줄 정보
     * 가게 이름,
     * 고객 이메일, 연락처
     * 예약일시, 승인여부, 방문여부
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Reservation {

        private final Long reservationId;

        private final String storeName;

        private final String customerEmail;
        private final String customerContact;

        private final LocalDateTime reserveDt;

        private final ReservationApproveStatus approveStatus;

        private final Boolean visitYn;

        public static Reservation fromDto(ReservationDto reservationDto) {
            return Reservation.builder()
                    .reservationId(reservationDto.getId())
                    .storeName(reservationDto.getStore().getName())
                    .customerEmail(reservationDto.getCustomer().getEmail())
                    .customerContact(reservationDto.getContactNumber())
                    .reserveDt(reservationDto.getReserveDt())
                    .approveStatus(reservationDto.getApproveStatus())
                    .visitYn(reservationDto.getVisitYn())
                    .build();
        }

    }
}
