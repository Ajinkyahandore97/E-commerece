package com.project.Ecommerce.util;


import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.*;


@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ImageNameValiator.class)
public @interface ImageNameValid {

    String message() default "{Invalid Image Name}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
