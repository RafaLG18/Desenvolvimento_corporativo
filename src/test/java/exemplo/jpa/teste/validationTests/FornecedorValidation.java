/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.validationTests;

import exemplo.jpa.Endereco;
import exemplo.jpa.Fornecedor;
import exemplo.jpa.Servico;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author rafael
 */
public class FornecedorValidation extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persisteFornecedorInvalido(){
        Fornecedor fornecedor = null;
        try {
            fornecedor = new Fornecedor();
            fornecedor.setCpf("999.888.777-66"); // CPF inválido diferente
            fornecedor.setLogin("fornecedor_empresa");
            fornecedor.setNome(""); // Nome em branco (inválido)
            fornecedor.setEmail("contato@empresa"); // Email inválido sem domínio
            fornecedor.setSenha("abc"); // Senha inválida (muito simples)
            fornecedor.addTelefone("(21)3333-4444");
            fornecedor.addTelefone("(21)5555-6666");
            fornecedor.addTelefone("(21)7777-8888");
            fornecedor.addTelefone("(21)9999-0000"); // Quarto telefone (excede limite)
            
            Endereco endereco = new Endereco();
            endereco.setLogradouro("Av. Empresarial");
            endereco.setNumero(500);
            endereco.setBairro("Centro Comercial");
            endereco.setCidade("São Paulo");
            endereco.setEstado("ZZ"); // Estado inválido
            endereco.setCep("12345-678"); // CEP inválido
            fornecedor.setEndereco(endereco);
            
            Servico servico1 = new Servico();
            servico1.setNome(""); // Nome em branco (inválido)
            servico1.setTipo("Consultoria");
            servico1.setPreco(150.0);
            fornecedor.addServico(servico1);
            
            Servico servico2 = new Servico();
            servico2.setNome("Desenvolvimento");
            servico2.setTipo(""); // Tipo em branco (inválido)
            servico2.setPreco(-50.0); // Preço negativo (pode ser inválido)
            fornecedor.addServico(servico2);
            
            em.persist(fornecedor);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Fornecedor.cpf: número do registro de contribuinte individual brasileiro (CPF) inválido"),
                                startsWith("class exemplo.jpa.Fornecedor.nome: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Fornecedor.email: deve ser um endereço de e-mail bem formado"),
                                startsWith("class exemplo.jpa.Fornecedor.senha: "),
                                startsWith("class exemplo.jpa.Fornecedor.telefones: tamanho deve ser entre 0 e 3"),
                                startsWith("class exemplo.jpa.Fornecedor.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.Fornecedor.endereco.cep: CEP inválido"),
                                startsWith("class exemplo.jpa.Fornecedor.servicos[0].nome: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Fornecedor.servicos[1].tipo: não deve estar em branco")
                        )
                );
            });

            assertNull(fornecedor.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizaFornecedorInvalido(){
        TypedQuery<Fornecedor> query = em.createQuery("SELECT f FROM Fornecedor f WHERE f.cpf like '333.222.111-88'", Fornecedor.class);
        Fornecedor fornecedor = query.getSingleResult();
        fornecedor.setNome(""); // Nome em branco (inválido)

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("não deve estar em branco", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
    
}
