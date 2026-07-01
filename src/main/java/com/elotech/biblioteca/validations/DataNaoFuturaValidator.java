package com.elotech.biblioteca.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;

import java.time.LocalDate;

public class DataNaoFuturaValidator
        implements ConstraintValidator<DataNaoFutura, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return true;
        }

        return !localDate.isAfter(LocalDate.now());
    }
}
