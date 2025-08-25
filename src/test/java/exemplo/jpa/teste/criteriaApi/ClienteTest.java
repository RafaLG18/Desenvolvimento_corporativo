/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Cliente;
import exemplo.jpa.Endereco;
import exemplo.jpa.Imagem;
import exemplo.jpa.Pedido;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    @Test
    public void ClienteDeSaoPaulo(){
        
        CriteriaQuery<Endereco> cqe=cb.createQuery(Endereco.class);
        cqe.select(root.get("endereco")).where(cb.equal(root.get("nome"),"Alice Lima"));
        
        TypedQuery<Endereco> query= em.createQuery(cqe);
        Endereco result=query.getSingleResult();
        
        assertEquals("SP",result.getEstado());
        
    }
    @Test
    public void ClientesEstadoSP(){
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(root.get("id")))
                .where(cb.equal(root.get("endereco").get("estado"), "SP"));
        
        TypedQuery<Long> query = em.createQuery(countQuery);
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("8"), resultado);
    }
    
    @Test
    public void ClienteJoinPedido(){
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Join<Cliente, Pedido> pedidoJoin = root.join("pedido");
        
        idQuery.select(root.get("id"));
        
        TypedQuery<Long> query = em.createQuery(idQuery);
        List<Long> clienteIds = query.getResultList();
        
        assertEquals(7, clienteIds.size());
    }
    
    @Test
    public void ClienteImagens() {
        logger.info("Executando ClienteImagens()");
        
        CriteriaQuery<Cliente> clienteQuery = cb.createQuery(Cliente.class);
        Root<Cliente> clienteRoot = clienteQuery.from(Cliente.class);
        
        clienteQuery.select(clienteRoot)
                   .where(cb.isNotEmpty(clienteRoot.get("imagens")));
        
        TypedQuery<Cliente> query = em.createQuery(clienteQuery);
        List<Cliente> clientes = query.getResultList();
        
        clientes.forEach(cliente -> {
            assertTrue(!cliente.getImages().isEmpty());
        });
        assertEquals(4, clientes.size());
    }
    
    @Test
    public void ClienteImagensSize() {
        logger.info("Executando ClienteImagensSize()");
        
        CriteriaQuery<Cliente> clienteQuery = cb.createQuery(Cliente.class);
        Root<Cliente> clienteRoot = clienteQuery.from(Cliente.class);
        
        clienteQuery.select(clienteRoot)
                   .where(cb.greaterThanOrEqualTo(cb.size(clienteRoot.get("imagens")), 4));
        
        TypedQuery<Cliente> query = em.createQuery(clienteQuery);
        List<Cliente> clientes = query.getResultList();
        
        clientes.forEach(cliente -> {
            assertTrue(!cliente.getImages().isEmpty());
        });
        assertEquals(1, clientes.size());
    }
    
    @Test
    public void ClientesDeSPOuRJ(){
        cq.select(root)
          .where(cb.or(
              cb.equal(root.get("endereco").get("estado"), "SP"),
              cb.equal(root.get("endereco").get("estado"), "RJ")
          ));
        
        TypedQuery<Cliente> query = em.createQuery(cq);
        List<Cliente> clientes = query.getResultList();
        assertEquals(11, clientes.size());
    }
    
    @Test
    public void ClientesDeSPERJ(){
        cq.select(root)
          .where(root.get("endereco").get("estado").in("SP", "RJ"));
        
        TypedQuery<Cliente> query = em.createQuery(cq);
        List<Cliente> clientes = query.getResultList();
        assertEquals(11, clientes.size());
    }
    
    @Test
    public void ImagensMemberOfCliente(){
        Imagem imagem = em.find(Imagem.class, Long.valueOf(3));
        
        cq.select(root)
          .where(cb.isMember(imagem, root.get("imagens")));
        
        TypedQuery<Cliente> query = em.createQuery(cq);
        Cliente cliente = query.getSingleResult();
        assertEquals(Long.valueOf("2"), cliente.getId());
    }
}
