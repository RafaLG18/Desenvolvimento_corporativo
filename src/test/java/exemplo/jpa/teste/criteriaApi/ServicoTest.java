/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Servico;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.Query;
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
public class ServicoTest extends Teste{
    CriteriaBuilder cb=null;
    Root<Servico> root=null;
    CriteriaQuery<Servico> cq=null;
    
    @Before
    public void setup(){
        cb = em.getCriteriaBuilder();
        cq=cb.createQuery(Servico.class);
        root=cq.from(Servico.class);        
    }
    
    @Test
    public void AServicoPorMaximo(){
        logger.info("Executando AServicoPorMaximo()");
        
        CriteriaQuery<Double> maxQuery = cb.createQuery(Double.class);
        Root<Servico> servicoRoot = maxQuery.from(Servico.class);
        
        maxQuery.select(cb.max(servicoRoot.get("preco")));
        
        TypedQuery<Double> query = em.createQuery(maxQuery);
        Double resultado = query.getSingleResult();
        
        assertEquals(Double.valueOf("1200.0"), resultado);
    }
    
    @Test
    public void BServicoPorMinimo(){
        logger.info("Executando BServicoPorMinimo()");
        
        CriteriaQuery<Double> minQuery = cb.createQuery(Double.class);
        Root<Servico> servicoRoot = minQuery.from(Servico.class);
        
        minQuery.select(cb.min(servicoRoot.get("preco")));
        
        TypedQuery<Double> query = em.createQuery(minQuery);
        Double resultado = query.getSingleResult();
        
        assertEquals(Double.valueOf("120.0"), resultado);
    }
    
    @Test
    public void QuantidadeDeServico(){
        logger.info("Executando QuantidadeDeServico()");
        
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(root.get("id")));
        
        TypedQuery<Long> query = em.createQuery(countQuery);
        Long resultado = query.getSingleResult();
        
        assertEquals(Long.valueOf("11"), resultado);
    }
    
    @Test
    public void SomaGanhoServicos(){
        logger.info("Executando SomaGanhoServicos()");
        
        CriteriaQuery<Double> sumQuery = cb.createQuery(Double.class);
        sumQuery.select(cb.sum(root.get("preco")));
        
        TypedQuery<Double> query = em.createQuery(sumQuery);
        Double resultado = query.getSingleResult();
        
        assertEquals(Double.valueOf("4600.0"), resultado);
    }
    
    @Test
    public void MediaGanhoServicos(){
        logger.info("Executando MediaGanhoServicos()");
        
        CriteriaQuery<Double> avgQuery = cb.createQuery(Double.class);
        avgQuery.select(cb.avg(root.get("preco")));
        
        TypedQuery<Double> query = em.createQuery(avgQuery);
        Double resultado = query.getSingleResult();
        
        assertEquals(Double.valueOf("418.1818181818182"), resultado);
    }
    
    private String getTipoQuantidade(Object[] resultado) {
        String servico = (String) resultado[0];
        Long quantidade = (Long) resultado[1];
        return servico + ": " + quantidade;
    }
    
    @Test
    public void GroupTipoServicos(){
        logger.info("Executando GroupTipoServicos()");
        
        CriteriaQuery<Object[]> groupQuery = cb.createQuery(Object[].class);
        Root<Servico> servicoRoot = groupQuery.from(Servico.class);
        
        groupQuery.multiselect(
            servicoRoot.get("tipo"),
            cb.count(servicoRoot.get("tipo"))
        )
        .groupBy(servicoRoot.get("tipo"))
        .orderBy(cb.asc(cb.count(servicoRoot.get("tipo"))));
        
        TypedQuery<Object[]> query = em.createQuery(groupQuery);
        List<Object[]> resultados = query.getResultList();
        
        assertEquals(3, resultados.size());
        assertEquals("Industrial: 3", getTipoQuantidade(resultados.get(0)));
        assertEquals("Residencial: 4", getTipoQuantidade(resultados.get(1)));
        assertEquals("Comercial: 4", getTipoQuantidade(resultados.get(2)));
    }
    
    @Test
    public void GroupTipoServicosHaving(){
        logger.info("Executando GroupTipoServicosHaving()");
        
        CriteriaQuery<Object[]> havingQuery = cb.createQuery(Object[].class);
        Root<Servico> servicoRoot = havingQuery.from(Servico.class);
        
        havingQuery.multiselect(
            servicoRoot.get("tipo"),
            cb.count(servicoRoot.get("tipo"))
        )
        .groupBy(servicoRoot.get("tipo"))
        .having(cb.greaterThan(cb.avg(servicoRoot.get("preco")), 500.00))
        .orderBy(cb.asc(cb.count(servicoRoot.get("tipo"))));
        
        TypedQuery<Object[]> query = em.createQuery(havingQuery);
        List<Object[]> resultados = query.getResultList();
        
        assertEquals(1, resultados.size());
        assertEquals("Residencial: 4", getTipoQuantidade(resultados.get(0)));
    }
}
