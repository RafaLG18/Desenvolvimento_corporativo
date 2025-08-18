/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.validationTests;

import exemplo.jpa.Servico;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author rafael
 */
public class ServicoValidation extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persisteServicoInvalido() {
        Servico servico = null;
        try {
            servico = new Servico();
            servico.setNome(""); // Nome em branco (inválido)
            servico.setTipo(""); // Tipo em branco (inválido)
            
            
            em.persist(servico);
            em.flush();
            
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Servico.nome: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Servico.tipo: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Servico.preco: não deve ser nulo")
                        )
                );
            });

            assertNull(servico.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizaServicoInvalido() {
        TypedQuery<Servico> query = em.createQuery("SELECT s FROM Servico s", Servico.class);
        query.setMaxResults(1);
        Servico servico = query.getSingleResult();
        servico.setNome(""); // Tornar nome em branco (inválido)
        
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("não deve estar em branco", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
    
}
