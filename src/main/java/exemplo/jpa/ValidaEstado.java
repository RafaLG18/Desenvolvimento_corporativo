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
@Constraint(validatedBy = ValidadorEstado.class)
@Documented
public @interface ValidaEstado {
    String message() default "{exemplo.jpa.Endereco.estado}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
