/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Pedido;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

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
    @Test
    public void PedidosComPix(){
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(root.get("id")))
                .where(cb.like(root.get("tipo"), "PIX"));
        TypedQuery<Long> query = em.createQuery(countQuery);
        Long resultado = query.getSingleResult();
        assertEquals(Long.valueOf("4"),resultado);
    }
    @Test
    public void PedidosComCartao(){
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(root.get("id")))
                .where(cb.like(root.get("tipo"), "CARTAO"));
        TypedQuery<Long> query = em.createQuery(countQuery);
        Long resultado = query.getSingleResult();
        assertEquals(Long.valueOf("2"),resultado);
    }
    
    @Test
    public void ComparacaoPedidos(){
        logger.info("Executando ComparacaoPedidos()");
        
        CriteriaQuery<Object[]> dateQuery = cb.createQuery(Object[].class);
        Root<Pedido> pedidoRoot = dateQuery.from(Pedido.class);
        
        dateQuery.select(cb.array(
            cb.max(pedidoRoot.get("data")),
            cb.min(pedidoRoot.get("data"))
        ));
        
        TypedQuery<Object[]> query = em.createQuery(dateQuery);
        Object[] resultado = query.getSingleResult();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String maiorData = dateFormat.format((Date) resultado[0]);
        String menorData = dateFormat.format((Date) resultado[1]);
        
        assertEquals("05-12-2025", maiorData);
        assertEquals("12-08-2025", menorData);
    }
}
