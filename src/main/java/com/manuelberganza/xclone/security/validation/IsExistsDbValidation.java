package com.manuelberganza.xclone.security.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.manuelberganza.xclone.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService != null) {
            return !userService.existsByUsername(value);
        }

        return true;
    }

}
