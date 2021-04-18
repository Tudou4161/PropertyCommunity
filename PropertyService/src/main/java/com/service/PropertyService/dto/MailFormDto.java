package com.service.PropertyService.dto;

import com.service.PropertyService.CustomAnnotation.EmailUniqueException;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MailFormDto {

    private String email;

}
