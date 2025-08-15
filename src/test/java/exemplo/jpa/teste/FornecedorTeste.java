/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste;

import exemplo.jpa.Cliente;
import exemplo.jpa.Endereco;
import exemplo.jpa.Fornecedor;
import exemplo.jpa.Servico;
import static exemplo.jpa.teste.Teste.logger;
import java.util.Collection;
import java.util.List;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FornecedorTeste extends Teste{
    @Test
    public void ApersistirFornecedor(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fulano da silva");
        fornecedor.setEmail("fulano@gmail.com");
        fornecedor.setLogin("fulano");
        fornecedor.setSenha("Teste@123");
        fornecedor.setCpf("152.792.090-90");
        fornecedor.addTelefone("888884325");
        
        // Inicializa a lista de imagens
        Servico servico = new Servico();
        servico.setNome("Passagem");
        servico.setTipo("Físico");
        servico.setPreco(301.20);
    
        fornecedor.addServico(servico);
        
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("PE");
        endereco.setCep("50.690-220");
        endereco.setNumero(550);
        fornecedor.setEndereco(endereco);
        
        em.persist(fornecedor);
        em.flush();
        assertNotNull(fornecedor.getId());
        assertNotNull(servico.getId());
    }
    @Test
    public void BconsultarFornecedor(){
        Fornecedor fornecedor = em.find(Fornecedor.class,Long.valueOf("3"));
        assertNotNull(fornecedor);
        assertEquals("Daniel Ferreira",fornecedor.getNome());
        assertEquals("daniel@email.com",fornecedor.getEmail());
        assertEquals("fornecedor1",fornecedor.getLogin());
        assertEquals("4321",fornecedor.getSenha());
        assertEquals("213.002.850-01",fornecedor.getCpf());
        
        Endereco endereco = fornecedor.getEndereco();
        assertNotNull(endereco);
        assertEquals("Travessa da Serra",endereco.getLogradouro());
        assertEquals("Industrial",endereco.getBairro());
        assertEquals("Manaus",endereco.getCidade());
        assertEquals("AM",endereco.getEstado());
        assertEquals("69000-001",endereco.getCep());
        assertEquals(Long.valueOf("5"),Long.valueOf(endereco.getNumero()));
        
        List<Servico> servicos= fornecedor.getServicos();
        for(Servico servico: servicos){
            assertEquals("Manutenção Elétrica",servico.getNome());
            assertEquals("Residencial",servico.getTipo());
            assertEquals("150.0",String.valueOf(servico.getPreco()));
        }
        
        Collection<String> telefones=fornecedor.getTelefones();
        for(String telefone:telefones){
            assertEquals("8881818212",telefone);
        }
    }
    
    @Test
    public void CatualizaFornecedor() {
        logger.info("Executando atualizaFornecedor()");
        Fornecedor fornecedor = em.find(Fornecedor.class, 3L);
        
        // Fix validation issues in existing data
        if (fornecedor.getEndereco() != null) {
            fornecedor.getEndereco().setEstado("AM");
            fornecedor.getEndereco().setCep("69.000-001");
        }
        
        fornecedor.setSenha("Nova@123");
        em.flush();
        em.clear(); // Limpa a cache para buscar no banco.
        Fornecedor fornecedorVerificado = em.find(Fornecedor.class, 3L);
        assertEquals("Nova@123", fornecedorVerificado.getSenha());
    }

    @Test
    public void DatualizaFornecedorMerge() {
        logger.info("Executando atualizaFornecedorMerge()");
        
        Fornecedor fornecedor = em.find(Fornecedor.class, 3L);
        assertNotNull(fornecedor);
        
        // Fix validation issues in existing data
        if (fornecedor.getEndereco() != null) {
            fornecedor.getEndereco().setEstado("AM");
            fornecedor.getEndereco().setCep("69.000-001");
        }

        em.clear(); 
        
        fornecedor.setEmail("daniel-ferreira@email.com");

        Fornecedor fornecedorGerenciado = em.merge(fornecedor); // Retorna uma cópia gerenciada

        em.flush(); // Força a atualização no banco de dados
        em.clear(); // Limpa a cache para buscar no banco. 
        fornecedor = em.find(Fornecedor.class, fornecedor.getId());
        assertEquals("daniel-ferreira@email.com", fornecedor.getEmail());
    }
    
    @Test
    public void EremoveFornecedor(){
        logger.info("Executando removeCliente()");
        Fornecedor fornecedor = em.find(Fornecedor.class, 4L);
        em.remove(fornecedor);
        em.flush();
//        em.clear();
        Fornecedor fornecedorApagado = em.find(Fornecedor.class, 4L);
        assertNull(fornecedorApagado);  
    }
}
