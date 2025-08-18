/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.validationTests;

import exemplo.jpa.Cliente;
import exemplo.jpa.Pedido;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Calendar;
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
public class PedidoValidation extends Teste {

    @Test(expected = ConstraintViolationException.class)
    public void persistePedidoInvalido() {
        Pedido pedido = null;
        try {
            Cliente cliente = null;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 2025);
            c.set(Calendar.MONTH, Calendar.AUGUST);
            c.set(Calendar.DAY_OF_MONTH, 25);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            pedido = new Pedido();
            pedido.setCliente(cliente); //cliente nulo
            
            pedido.setData(c.getTime()); // data futura
           
            pedido.setTipo(null);// Tipo nulo
            em.persist(pedido);
            em.flush();

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Pedido.cliente: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pedido.tipo: não deve ser nulo"),
                                startsWith("class exemplo.jpa.Pedido.data: deve ser uma data passada")
                        )
                );
            });

            assertNull(pedido.getId());
            throw ex;

        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizaPedidoInvalido() {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        query.setMaxResults(1);
        Pedido pedido = query.getSingleResult();
        pedido.setTipo(null); // Tornar tipo nulo (inválido)
        
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("não deve ser nulo", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }

}
