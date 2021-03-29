package com.service.PropertyService.dto;

import com.service.PropertyService.domain.Category;
import com.service.PropertyService.domain.TransactionType;
import lombok.Data;

@Data
public class PostSearchCondition {

    private String firstAddr;
    private String secondAddr;
    private String subAddress;
    private Category category;
    private TransactionType transactionType;

}
