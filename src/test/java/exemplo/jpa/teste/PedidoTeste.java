/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste;

import exemplo.jpa.Cliente;
import exemplo.jpa.Fornecedor;
import exemplo.jpa.Pedido;
import exemplo.jpa.TipoPagamento;
import static exemplo.jpa.teste.Teste.logger;
import java.util.Calendar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author rafael
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PedidoTeste extends Teste{
    @Test
    public void ApersistirPedido(){
        Pedido pedido = new Pedido();
        Cliente cliente = em.find(Cliente.class, 1L);
        assertNotNull(cliente);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2025);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);


        pedido.setData(c.getTime());
        pedido.setTipo(TipoPagamento.CARTAO);
        pedido.setCliente(cliente);
        
        cliente.setPedido(pedido);
        em.persist(pedido);
 
        em.flush();
        assertNotNull(cliente.getId());
        assertNotNull(pedido.getId());    
    }
    
    @Test
    public void BconsultarPedido(){
        Pedido pedido = em.find(Pedido.class, Long.valueOf("3"));
        
        Cliente cliente = em.find(Cliente.class, Long.valueOf("1'"));
        Cliente clientePedido=pedido.getCliente();
        
        assertEquals(clientePedido.getId(),cliente.getId());
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2025);
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DAY_OF_MONTH, 13);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        assertEquals(c.getTime(),pedido.getData());
        assertEquals("CARTAO",String.valueOf(pedido.getTipo()));
        
        
    }
    
    @Test
    public void CatualizaPedido() {
        logger.info("Executando atualizaPedido()");
        Pedido pedido = em.find(Pedido.class, 1L);
        pedido.setTipo(TipoPagamento.CARTAO);
        em.flush();
        em.clear(); // Limpa a cache para buscar no banco.
        Pedido pedidoVerificado = em.find(Pedido.class, 1L);
        assertEquals(TipoPagamento.CARTAO, pedidoVerificado.getTipo());
    }

    @Test
    public void DatualizaPedidoMerge() {
        logger.info("Executando atualizaPedidoMerge()");
        
        Pedido pedido = em.find(Pedido.class, 2L);
        assertNotNull(pedido);

        em.clear(); 
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2025);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        pedido.setData(c.getTime());

        Pedido pedidoGerenciado = em.merge(pedido); // Retorna uma cópia gerenciada

        em.flush(); // Força a atualização no banco de dados
        em.clear(); // Limpa a cache para buscar no banco. 
        pedido = em.find(Pedido.class, pedido.getId());
        assertEquals(c.getTime(), pedido.getData());
    }
    
    @Test
    public void EremoveFornecedor(){
        logger.info("Executando removePedido()");
        Pedido pedido = em.find(Pedido.class, 1L);
        em.remove(pedido);
        em.flush();
//        em.clear();
        Pedido pedidoApagado = em.find(Pedido.class, 1L);
        assertNull(pedidoApagado);  
    }
}
