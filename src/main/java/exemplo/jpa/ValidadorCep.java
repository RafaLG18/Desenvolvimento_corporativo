package exemplo.jpa;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCep implements ConstraintValidator<ValidaCep, String> {
    
    @Override
    public void initialize(ValidaCep validaCep) {
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null) {
            return false;
        }
        
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        
        if (cepLimpo.length() != 8) {
            return false;
        }
        
        if (cepLimpo.matches("00000000|11111111|22222222|33333333|44444444|55555555|66666666|77777777|88888888|99999999")) {
            return false;
        }
        
        return cep.matches("\\d{5}-\\d{3}") || cep.matches("\\d{2}\\.\\d{3}-\\d{3}");
    }
}