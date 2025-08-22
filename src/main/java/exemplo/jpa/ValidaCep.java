package exemplo.jpa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorCep.class)
@Documented
public @interface ValidaCep {
    String message() default "{exemplo.jpa.Endereco.cep}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}