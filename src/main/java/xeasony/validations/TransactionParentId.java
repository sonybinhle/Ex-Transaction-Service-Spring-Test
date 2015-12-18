package xeasony.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionParentIdValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionParentId {
    String message() default "Parent_Id not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}