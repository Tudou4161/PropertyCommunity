package com.service.PropertyService.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    //도로명주소 full 이름
    private String roadAddr;

    //특별시, 광역시, 도
    private String firstAddr;

    //시, 군, 구
    private String secondAddr;

    //나머지 도로명 + 번지
    private String thirdAddr;

    //건물이름
    private String subAddress;

}
