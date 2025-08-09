package exemplo.jpa.teste.jpqltests;

import exemplo.jpa.Cliente;
import exemplo.jpa.Endereco;
import exemplo.jpa.Imagem;
import static exemplo.jpa.teste.jpqltests.GenericTest.logger;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafael
 */
public class ClienteTests extends GenericTest{
    @Test
    public void QuantidadeDeClientes(){
        logger.info("Executando QuantidadeDeClientes()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT Count(c.id) FROM Cliente c ",
                Long.class);   
        Long resultado = query.getSingleResult();

        assertEquals(Long.valueOf("11"),resultado);
    }
    
    @Test
    public void ClienteDeSaoPaulo(){
        logger.info("Executando ClienteDeSaoPaulo()");
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT c.endereco FROM Cliente c WHERE c.nome LIKE 'Alice Lima' ",
                Endereco.class);   
        Endereco resultado= query.getSingleResult();
        
        assertEquals("SP",resultado.getEstado());
    }
    
    @Test
    public void ClientesEstadoSP(){
        logger.info("Executando ClientesPorEstado()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c.id) FROM Cliente c WHERE c.endereco.estado = 'SP' ",
                Long.class);   
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("8"),resultado);
    }
    
    @Test
    public void ClienteJoinPedido(){
        logger.info("Executando ClienteJoinPedido()");
        
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c.id FROM Cliente c JOIN c.pedido p ",
                Cliente.class);   
        List<Cliente> clientes = query.getResultList();
        
        assertEquals(7,clientes.size());
        
    }
    
    @Test
    public void ClienteLeftJoinPedido(){
        logger.info("Executando ClienteLeftJoinPedido()");
        
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c LEFT JOIN c.pedido p ",
                Cliente.class);   
        List<Cliente> clientes = query.getResultList();
        
        assertEquals(11,clientes.size());   
    }
    
    @Test
    public void ClienteImagens() {
        logger.info("Executando ClienteImagens()");
        TypedQuery<Cliente> query;
        query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.imagens IS NOT EMPTY",
                Cliente.class);
        List<Cliente> clientes = query.getResultList();
        
        clientes.forEach(cliente -> {
            assertTrue(!cliente.getImages().isEmpty());
        });
        assertEquals(4, clientes.size());
    }
    
    @Test
    public void ClienteImagensSize() {
        logger.info("Executando ClienteImagensSize()");
        TypedQuery<Cliente> query;
        query = em.createQuery(
                "SELECT c FROM Cliente c WHERE SIZE(c.imagens) >=4",
                Cliente.class);
        List<Cliente> clientes = query.getResultList();
        
        clientes.forEach(cliente -> {
            assertTrue(!cliente.getImages().isEmpty());
        });
        assertEquals(1, clientes.size());
    }
    
    @Test
    public void ClienteNamedQueryPedido(){
        logger.info("Executando ClienteImagensSize()");
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.Pedido", Cliente.class);
        query.setParameter("tipo","PIX");
        List<Cliente> clientes = query.getResultList();
      
        assertEquals(4, clientes.size());
    }
    
    @Test
    public void ImagensMemberOfCliente(){
        Imagem imagem = em.find(Imagem.class, Long.valueOf(3));
        TypedQuery<Cliente> query;
        query = em.createQuery(
                "SELECT c FROM Cliente c WHERE :imagem MEMBER OF c.imagens",
                Cliente.class);
        query.setParameter("imagem", imagem);
        Cliente cliente = query.getSingleResult();
        assertEquals(Long.valueOf("2"), cliente.getId());
    }
    
    @Test
    public void ClientesDeSPERJ(){
        TypedQuery<Cliente> query;
        query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.endereco.estado IN ('SP','RJ')",
                Cliente.class);
        List<Cliente> clientes = query.getResultList();
        assertEquals(11, clientes.size());
    }
    @Test
    public void ClientesDeSPOuRJ(){
        TypedQuery<Cliente> query;
        query = em.createQuery(
                "SELECT c FROM Cliente c WHERE c.endereco.estado ='SP' OR c.endereco.estado ='RJ'",
                Cliente.class);
        List<Cliente> clientes = query.getResultList();
        assertEquals(11, clientes.size());
    }
}
