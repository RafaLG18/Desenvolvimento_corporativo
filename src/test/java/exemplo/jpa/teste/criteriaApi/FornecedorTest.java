/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Endereco;
import exemplo.jpa.Fornecedor;
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
public class FornecedorTest extends Teste{
    CriteriaBuilder cb=null;
    Root<Fornecedor> root=null;
    CriteriaQuery<Fornecedor> cq=null;
    
    @Before
    public void setup(){
        cb = em.getCriteriaBuilder();
        cq=cb.createQuery(Fornecedor.class);
        root=cq.from(Fornecedor.class);        
    }
    
    @Test
    public void QuantidadeDeFornecedores(){
        cq.select(root);
        TypedQuery<Fornecedor> tq=em.createQuery(cq);
        List<Fornecedor> fornecedores = tq.getResultList();
        
        assertEquals(6,fornecedores.size());
    }
    
    @Test
    public void FornecedorSemComplemento(){
        logger.info("Executando FornecedorSemComplemento()");
        
        CriteriaQuery<Endereco> enderecoQuery = cb.createQuery(Endereco.class);
        Root<Fornecedor> fornecedorRoot = enderecoQuery.from(Fornecedor.class);
        
        enderecoQuery.select(fornecedorRoot.get("endereco"))
                     .where(cb.like(fornecedorRoot.get("nome"), "Daniel Ferreira"));
        
        TypedQuery<Endereco> query = em.createQuery(enderecoQuery);
        Endereco resultado = query.getSingleResult();
        
        assertEquals("", resultado.getComplemento());
    }
    
    @Test
    public void FornecedoresPorEstado(){
        CriteriaQuery<Long> CountQuery = cb.createQuery(Long.class);
        Root<Fornecedor> fornecedorRoot = CountQuery.from(Fornecedor.class);
        
        CountQuery.select(cb.count(fornecedorRoot))
                .where(cb.equal(fornecedorRoot.get("endereco").get("estado"), "AM"));
        
        TypedQuery<Long> query = em.createQuery(CountQuery);
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("2"), resultado);
    }
    
    @Test
    public void FornecedorNomeDan(){
        logger.info("Executando FornecedorNomeDan()");
        
        CriteriaQuery<String> nomeQuery = cb.createQuery(String.class);
        Root<Fornecedor> fornecedorRoot = nomeQuery.from(Fornecedor.class);
        
        nomeQuery.select(fornecedorRoot.get("nome"))
                 .where(cb.equal(cb.substring(fornecedorRoot.get("nome"), 1, 3), "Dan"));
        
        TypedQuery<String> query = em.createQuery(nomeQuery);
        List<String> resultado = query.getResultList();
        
        assertEquals(2, resultado.size());
        for(String nome : resultado){
            assertEquals("Daniel", nome.substring(0, 6));
        }
    }
}
