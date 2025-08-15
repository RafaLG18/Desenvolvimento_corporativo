/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste;

import exemplo.jpa.Cliente;
import exemplo.jpa.Endereco;
import exemplo.jpa.Imagem;
import exemplo.jpa.Pedido;
import exemplo.jpa.TipoPagamento;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author rafael
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTeste extends Teste {

    @Test
    public void ApersistirCliente() {
        logger.info("Executando persistirrCliente()");
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano da silva sauro barbosa");
        cliente.setEmail("fulano-sauro-barbosa@gmail.com");
        cliente.setLogin("fulano-sauro-barbosa");
        cliente.setSenha("Teste1@barbosa");
        cliente.setCpf("342.645.800-48");
        cliente.addTelefone("888884325");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setComplemento("bloco b");
        endereco.setCidade("Recife");
        endereco.setEstado("PE");
        endereco.setCep("50.690-220");
        endereco.setNumero(550);
        cliente.setEndereco(endereco);

        // Cria uma imagem de exemplo
//        Imagem img = new Imagem();
//        img.setImagem("imagem-teste".getBytes()); // Simula conteúdo binário
//        cliente.addImage(img);
//        img.setCliente(cliente);
//        cliente.getImages().add(img);
        // Cria pedido
        Pedido pedido = new Pedido();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);

        pedido.setData(c.getTime());
        pedido.setTipo(TipoPagamento.PIX);
        cliente.setPedido(pedido);

        em.persist(cliente);
        em.flush();
        assertNotNull(cliente.getId());
        assertNotNull(pedido.getId());
    }

    @Test
    public void BconsultarCliente() {
        logger.info("Executando consultarCliente()");
        Cliente cliente = em.find(Cliente.class, Long.valueOf("1"));
        assertNotNull(cliente);
        assertEquals("Alice Lima", cliente.getNome());
        assertEquals("alice@email.com", cliente.getEmail());
        assertEquals("cliente1", cliente.getLogin());
        assertEquals("1234", cliente.getSenha());
        assertEquals("065.173.480-00", cliente.getCpf());

        Endereco endereco = cliente.getEndereco();
        assertNotNull(endereco);
        assertEquals("Rua das Flores", endereco.getLogradouro());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("SP", endereco.getEstado());
        assertEquals("01010-000", endereco.getCep());
        assertEquals(Long.valueOf("10"), Long.valueOf(endereco.getNumero()));

        Collection<String> telefones = cliente.getTelefones();
        for (String telefone : telefones) {
            assertEquals("88818182", telefone);
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2025);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 12);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Pedido pedido = cliente.getPedido();
        assertEquals(c.getTime(), pedido.getData());
        assertEquals("PIX", String.valueOf(pedido.getTipo()));
//        List<Imagem> imagens=cliente.getImages();
//        assertNotNull(imagens);
//        for(Imagem imagem:imagens){
//            assertEquals("QmluYXJ5SW1hZ2Ux",imagem.getImage());
//        }

    }

    @Test
    public void CatualizaCliente() {
        logger.info("Executando atualizaCliente()");
        Cliente cliente = em.find(Cliente.class, 2L);
        
        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setEstado("SP");
            cliente.getEndereco().setCep("01.010-000");
        }
        
        cliente.setSenha("Nova@123");
        em.flush();
        em.clear(); // Limpa a cache para buscar no banco.
        Cliente clienteVerificado = em.find(Cliente.class, 2L);
        assertEquals("Nova@123", clienteVerificado.getSenha());
    }

    @Test
    public void DatualizaClienteMerge() {
        logger.info("Executando atualizaClienteMerge()");
        
        Cliente cliente = em.find(Cliente.class, 2L);
        assertNotNull(cliente);
        
        if (cliente.getEndereco() != null) {
            cliente.getEndereco().setEstado("SP");
            cliente.getEndereco().setCep("01.010-000");
        }

        em.clear(); 
        
        cliente.setEmail("alice-maria@email.com");

        Cliente clienteGerenciado = em.merge(cliente); // Retorna uma cópia gerenciada

        em.flush(); // Força a atualização no banco de dados
        em.clear(); // Limpa a cache para buscar no banco. 
        cliente = em.find(Cliente.class, cliente.getId());
        assertEquals("alice-maria@email.com", cliente.getEmail());
    }
    
    @Test
    public void EremoveCliente(){
        logger.info("Executando removeCliente()");
        Cliente cliente = em.find(Cliente.class, 2L);
        em.remove(cliente);
        em.flush();
//        em.clear();
        Cliente clienteApagado = em.find(Cliente.class, 2L);
        assertNull(clienteApagado);  
    }
}
