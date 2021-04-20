package com.service.PropertyService.dto;

import com.service.PropertyService.CustomAnnotation.EmailUniqueException;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    @EmailUniqueException
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6-12자 이내로 입력해주세요.")
    private String password;

    private String auth;

    @NotBlank(message = "본명을 입력해주세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    @Builder
    public UserDto(String email, String password, String name,
                   String auth, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
