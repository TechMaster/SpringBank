package com.xbank.validation.validator;

import com.xbank.validation.annotation.NullOrNotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    @Override
    public void initialize(NullOrNotBlank parameters) {
        //no-op
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }

        boolean isAllWhitespace = value.matches("^\\s*$");
        return !isAllWhitespace;
    }
}
