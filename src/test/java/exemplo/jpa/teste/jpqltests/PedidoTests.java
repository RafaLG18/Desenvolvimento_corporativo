/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.jpqltests;

import exemplo.jpa.Cliente;
import exemplo.jpa.Pedido;
import static exemplo.jpa.teste.jpqltests.GenericTest.logger;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rafael
 */
public class PedidoTests extends GenericTest{
    
    @Test
    public void PedidosComCartao(){
        logger.info("Executando PedidosComCartao()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p.id) FROM Pedido p WHERE p.tipo LIKE 'CARTAO'",
                Long.class);
        Long resultado = query.getSingleResult();

        assertEquals(Long.valueOf("2"),resultado);
    }
    
    @Test
    public void PedidosComPix(){
        logger.info("Executando PedidosComPix()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p.id) FROM Pedido p WHERE p.tipo LIKE 'PIX'",
                Long.class);
        Long resultado = query.getSingleResult();

        assertEquals(Long.valueOf("4"),resultado);
    }
    
    @Test
    public void ComparacaoPedidos(){
        logger.info("Executando ComparacaoPedidos()");
        Query query = em.createQuery(
                "SELECT MAX(p.data), MIN(p.data) FROM Pedido p");
        Object[] resultado = (Object[]) query.getSingleResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String maiorData = dateFormat.format((Date) resultado[0]);
        String menorData = dateFormat.format((Date) resultado[1]);
        assertEquals("05-12-2025", maiorData);
        assertEquals("12-08-2025", menorData);
    }
    

    
    

}
