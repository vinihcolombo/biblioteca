package com.elotech.biblioteca.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataNaoFuturaValidator.class)
@Documented
public @interface DataNaoFutura {

    String message() default "A data não pode ser futura.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
