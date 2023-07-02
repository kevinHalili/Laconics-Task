package com.task.schoolservis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;


@Getter
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private Date expiredDate;
    private String refreshToken;
    private Date refreshTokenExp;

}
