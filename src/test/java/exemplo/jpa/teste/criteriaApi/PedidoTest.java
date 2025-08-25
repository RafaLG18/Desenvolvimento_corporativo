/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Pedido;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Before;

/**
 *
 * @author rafael
 */
public class PedidoTest extends Teste{
    CriteriaBuilder cb=null;
    Root<Pedido> root=null;
    CriteriaQuery<Pedido> cq=null;
    
    @Before
    public void setup(){
        cb = em.getCriteriaBuilder();
        cq=cb.createQuery(Pedido.class);
        root=cq.from(Pedido.class);        
    }
    
}
