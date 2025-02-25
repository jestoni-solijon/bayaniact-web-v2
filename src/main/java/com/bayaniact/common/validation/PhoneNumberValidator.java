package com.bayaniact.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        // No need to initialize anything since there's no configurable prefix
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Phone number is required
        }

        // Check if the phone number starts with "09" and is exactly 11 digits long
        return value.matches("^09\\d{9}$");
    }
}


