/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste;

import exemplo.jpa.Cliente;
import exemplo.jpa.Fornecedor;
import exemplo.jpa.Servico;
import static exemplo.jpa.teste.Teste.logger;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class ServicoTeste extends Teste {

    @Test
    public void ApersistirServico() {
        logger.info("Executando PersisteServico()");
        Servico servico = new Servico();
        servico.setNome("Passagem de cabo");
        servico.setPreco(300.00);
        servico.setTipo("REDES");
        Fornecedor fornecedor = em.find(Fornecedor.class, Long.valueOf("3"));
        fornecedor.addServico(servico);
        servico.addFornecedor(fornecedor);
        em.persist(servico);
        
        em.flush();
        assertNotNull(servico.getId());
        assertNotNull(fornecedor.getId());

    }

    @Test
    public void BconsultarServico() {
        logger.info("Executando ConsultaServico()");
        Servico servico = em.find(Servico.class, 1L); // ajuste ID conforme seus dados
        assertNotNull(servico);
        assertEquals("Manutenção Elétrica", servico.getNome());
        assertEquals(150.00, servico.getPreco(), 0.01);
        assertEquals("Residencial", servico.getTipo());

        // Agora é possível acessar os fornecedores
        List<Fornecedor> fornecedores = servico.getFornecedores();
        assertNotNull(fornecedores);
        assertFalse(fornecedores.isEmpty());

        Fornecedor f = fornecedores.get(0);
        assertNotNull(f.getId());
    }
    
     @Test
    public void CatualizaServico() {
        logger.info("Executando atualizaServico()");
        Servico servico = em.find(Servico.class, 4L);
        servico.setNome("Instalação cabos");
        em.flush();
        em.clear(); // Limpa a cache para buscar no banco.
        Servico servicoVerificado = em.find(Servico.class, 4L);
        assertEquals("Instalação cabos", servicoVerificado.getNome());
    }

    @Test
    public void DatualizaServicoMerge() {
        logger.info("Executando atualizaServicoMerge()");
        
        Servico servico = em.find(Servico.class, 4L);
        assertNotNull(servico);

        em.clear(); 
        
        servico.setPreco(500);

        Servico servicoGerenciado = em.merge(servico); // Retorna uma cópia gerenciada

        em.flush(); // Força a atualização no banco de dados
        em.clear(); // Limpa a cache para buscar no banco. 
        servico = em.find(Servico.class, servico.getId());
        assertEquals(500.00, servico.getPreco(), 0.0001);
    }
    
    @Test
    public void EremoveServico(){
        logger.info("Executando removeServico()");
        Servico servico = em.find(Servico.class, 1L);
        em.remove(servico);
        em.flush();
//        em.clear();
        Servico servicoApagado = em.find(Servico.class, 1L);
        assertNull(servicoApagado);  
    }
}
