package com.dev.springboottesting.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;


    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(o);
        Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
        Object secondObj = beanWrapper.getPropertyValue(secondFieldName);

        boolean isValid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation();
        }

        return isValid;

    }


}
