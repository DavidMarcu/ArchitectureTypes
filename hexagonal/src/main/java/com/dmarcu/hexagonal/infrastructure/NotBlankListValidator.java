package com.dmarcu.hexagonal.infrastructure;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotBlankListValidator implements ConstraintValidator<NotBlankListElements, List<String>> {

    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        return strings.stream().allMatch(StringUtils::isNotBlank);
    }
}
