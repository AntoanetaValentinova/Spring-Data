package com.jsonprocessing.demo.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }


    @Override
    public <E> boolean isValid(E entity) {
        return this.violations(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return this.validator.validate(entity);
    }
}
