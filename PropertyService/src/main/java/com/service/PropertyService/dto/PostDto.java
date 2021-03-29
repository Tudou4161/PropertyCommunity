package com.service.PropertyService.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.service.PropertyService.domain.Address;
import com.service.PropertyService.domain.Category;
import com.service.PropertyService.domain.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter @Setter
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank(message = "필수 입력란입니다.")
    private String title;

    @NotBlank(message = "필수 입력란입니다.")
    private String content;

    private String author;

    private double latitude;
    private double longitude;

    private Long likeCnt;
    private Long findCnt;

    @NotNull(message = "필수 입력란입니다.")
    private Integer grade;

    @NotBlank(message = "필수 입력란입니다.")
    private String roadAddr;

    private String firstAddr;
    private String secondAddr;
    private String thirdAddr;

    @NotBlank(message = "필수 입력란입니다.")
    private String subAddress;

//    @NotBlank(message = "필수 입력란입니다.")
    private Category category;

//    @NotBlank(message = "필수 입력란입니다.")
    private TransactionType transactionType;

    @QueryProjection
    public PostDto(String author, String title, String content,
                   String firstAddr, String secondAddr, String thirdAddr, String subAddress,
                   Category category, TransactionType transactionType) {

        this.author = author;
        this.title = title;
        this.content = content;
        this.firstAddr = firstAddr;
        this.secondAddr = secondAddr;
        this.thirdAddr = thirdAddr;
        this.subAddress = subAddress;
        this.category = category;
        this.transactionType = transactionType;

    }
}
