package com.example.demo;

import lombok.Data;

@Data
public class Weather {

	private Double latitude;// x좌표
    private Double longitude;// y좌표
    private String weatherImgUrl;//이미지
    private String address; //주소
    private Double temperature; //온도
}
