/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Cliente;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author rafael
 */
public class ClienteTest extends Teste {
    
    CriteriaBuilder cb=null;
    Root<Cliente> root=null;
    CriteriaQuery<Cliente> cq=null;
    
    @Before
    public void setup(){
        cb = em.getCriteriaBuilder();
        cq=cb.createQuery(Cliente.class);
        root=cq.from(Cliente.class);        
    }
    @Test
    public void QuantidadeDeClientes(){
        cq.select(root);
        TypedQuery<Cliente> tq=em.createQuery(cq);
        List<Cliente> clientes = tq.getResultList();
        
        assertEquals(11,clientes.size());
    }  
    
    
    
}
