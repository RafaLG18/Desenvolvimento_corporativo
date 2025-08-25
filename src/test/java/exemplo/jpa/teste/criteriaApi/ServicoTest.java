/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.criteriaApi;

import exemplo.jpa.Servico;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Before;

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
    
}
