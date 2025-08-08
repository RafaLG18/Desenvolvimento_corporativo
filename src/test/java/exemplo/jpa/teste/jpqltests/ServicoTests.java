/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.jpqltests;

import exemplo.jpa.Servico;
import static exemplo.jpa.teste.jpqltests.GenericTest.logger;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author rafael
 */
public class ServicoTests extends GenericTest{
    @Test
    public void AServicoPorMaximo(){
        logger.info("Executando AServicoPorMaximo()");
        TypedQuery<Double> query = em.createQuery(
                "SELECT MAX(s.preco) FROM Servico s ",
                Double.class);
        Double resultado = query.getSingleResult();

        assertEquals(Double.valueOf("1200.0"),resultado);
    }
    
    @Test
    public void BServicoPorMinimo(){
        logger.info("Executando BServicoPorMinimo()");
        TypedQuery<Double> query = em.createQuery(
                "SELECT MIN(s.preco) FROM Servico s ",
                Double.class);
        Double resultado = query.getSingleResult();

        assertEquals(Double.valueOf("120.0"),resultado);
    }
    
    @Test
    public void QuantidadeDeServico(){
        logger.info("Executando QuantidadeDeServico()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT Count(s.id) FROM Servico s ",
                Long.class);   
        Long resultado = query.getSingleResult();

        assertEquals(Long.valueOf("11"),resultado);
    }
    
    @Test
    public void SomaGanhoServicos(){
        logger.info("Executando SomaGanhoServicos()");
        TypedQuery<Double> query = em.createQuery(
                "SELECT SUM(s.preco) FROM Servico s ",
                Double.class);   
        Double resultado = query.getSingleResult();

        assertEquals(Double.valueOf("4600.0"),resultado);
    }
    
    @Test
    public void MediaGanhoServicos(){
        logger.info("Executando SomaGanhoServicos()");
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(s.preco) FROM Servico s ",
                Double.class);   
        Double resultado = query.getSingleResult();

        assertEquals(Double.valueOf("418.1818181818182"),resultado);
    }
    
    private String getTipoQuantidade(Object[] resultado) {
        String servico = (String) resultado[0];
        Long quantidade = (Long) resultado[1];
        return servico + ": " + quantidade;
    }
    
    @Test
    public void GroupTipoServicos(){
        logger.info("Executando SomaGanhoServicos()");
        Query query = em.createQuery("SELECT s.tipo,COUNT(s.tipo) FROM Servico s GROUP BY s.tipo ORDER BY COUNT(s.tipo)");
        List<Object[]> resultados = query.getResultList();
        assertEquals(3, resultados.size());
        assertEquals("Industrial: 3", getTipoQuantidade(resultados.get(0)));
        assertEquals("Residencial: 4", getTipoQuantidade(resultados.get(1)));
        assertEquals("Comercial: 4", getTipoQuantidade(resultados.get(2)));
        
    }
    
    @Test
    public void GroupTipoServicosHaving(){
        logger.info("Executando SomaGanhoServicos()");
        Query query = em.createQuery("SELECT s.tipo,COUNT(s.tipo) FROM Servico s GROUP BY s.tipo HAVING AVG(s.preco)>500.00  ORDER BY COUNT(s.tipo) ");
        List<Object[]> resultados = query.getResultList();
        assertEquals(1, resultados.size());
        assertEquals("Residencial: 4", getTipoQuantidade(resultados.get(0)));
        
    }
    
}
