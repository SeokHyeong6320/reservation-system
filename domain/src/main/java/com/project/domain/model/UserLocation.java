package com.project.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserLocation {

    private Double lat;
    private Double lon;

}
