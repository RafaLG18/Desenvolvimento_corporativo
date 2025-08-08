/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.jpqltests;

import exemplo.jpa.Endereco;
import static exemplo.jpa.teste.jpqltests.GenericTest.logger;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author rafael
 */
public class FornecedoresTests extends GenericTest{
    
    @Test
    public void FornecedorNomeDan(){
        logger.info("Executando FornecedorNomeDan()");
        TypedQuery<String> query = em.createQuery(
                "SELECT f.nome FROM Fornecedor f WHERE SUBSTRING(f.nome,1,3)='Dan' ",
                String.class);   
        List<String> resultado= query.getResultList();
        assertEquals(2, resultado.size());
        for( String nome:resultado){
            assertEquals("Daniel",nome.substring(0,6));
        }
    }
    
    @Test
    public void FornecedorSemComplemento(){
        logger.info("Executando FornecedorSemComplemento()");
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT f.endereco FROM Fornecedor f WHERE f.nome LIKE 'Daniel Ferreira' ",
                Endereco.class);   
        Endereco resultado= query.getSingleResult();
        
        assertEquals("",resultado.getComplemento());
    }
    
    @Test
    public void QuantidadeDeFornecedores(){
        logger.info("Executando QuantidadeDeFornecedores()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f.id) FROM Fornecedor f ",
                Long.class);   
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("6"),resultado);
    }
    
    @Test
    public void FornecedoresPorEstado(){
        logger.info("Executando FornecedoresPorEstado()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f.id) FROM Fornecedor f WHERE f.endereco.estado = 'AM' ",
                Long.class);   
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("2"),resultado);
    }
    
}
