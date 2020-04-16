package com.dmarcu.hexagonal.infrastructure;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBlankListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankListElements {
    String message() default "Blank list element not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
