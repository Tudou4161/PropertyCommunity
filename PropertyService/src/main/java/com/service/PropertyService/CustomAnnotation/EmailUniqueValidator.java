package com.service.PropertyService.CustomAnnotation;

import com.service.PropertyService.Repository.UserRepository;
import com.service.PropertyService.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

public class EmailUniqueValidator implements ConstraintValidator<EmailUniqueException, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User findMembers = userRepository.findByUserEmail(email);

        boolean isExistMember = (findMembers != null)? true : false;

        if (isExistMember == true) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    MessageFormat.format("동일한 메일이 이미 존재합니다.", email))
                    .addConstraintViolation();
        }
        //그게 아니면, 허용시킨다.
        return !isExistMember;
    }
}
