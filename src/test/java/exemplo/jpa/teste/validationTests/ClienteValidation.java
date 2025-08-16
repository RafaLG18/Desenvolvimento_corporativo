/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa.teste.validationTests;

import exemplo.jpa.Cliente;
import exemplo.jpa.Endereco;
import exemplo.jpa.Pedido;
import exemplo.jpa.TipoPagamento;
import exemplo.jpa.teste.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
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
public class ClienteValidation extends Teste {
    
    @Test(expected = ConstraintViolationException.class)
    public void persiteClienteInvalido(){
        Cliente cliente = null;
        try {
            cliente = new Cliente();
            cliente.setCpf("123.456.789-00"); // CPF inválido
            cliente.setLogin("cliente_teste");
            cliente.setNome(""); // Nome em branco (inválido)
            cliente.setEmail("email_invalido"); // Email inválido
            cliente.setSenha("123"); // Senha inválida (muito curta e sem complexidade)
            cliente.addTelefone("(11)99999-9999");
            cliente.addTelefone("(11)88888-8888");
            cliente.addTelefone("(11)77777-7777");
            cliente.addTelefone("(11)66666-6666"); // Quarto telefone (excede limite de 3)
            
            Endereco endereco = new Endereco();
            endereco.setLogradouro("Rua Teste");
            endereco.setNumero(123);
            endereco.setBairro("Bairro Teste");
            endereco.setCidade("Cidade Teste");
            endereco.setEstado("XX"); // Estado inválido
            endereco.setCep("00000-000"); // CEP inválido
            cliente.setEndereco(endereco);
            
            Pedido pedido = new Pedido();
            pedido.setTipo(TipoPagamento.CARTAO);
            pedido.setData(new Date());
            cliente.setPedido(pedido);
            
            em.persist(cliente);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Cliente.cpf: número do registro de contribuinte individual brasileiro (CPF) inválido"),
                                startsWith("class exemplo.jpa.Cliente.nome: não deve estar em branco"),
                                startsWith("class exemplo.jpa.Cliente.email: deve ser um endereço de e-mail bem formado"),
                                startsWith("class exemplo.jpa.Cliente.senha: "),
                                startsWith("class exemplo.jpa.Cliente.telefones: tamanho deve ser entre 0 e 3"),
                                startsWith("class exemplo.jpa.Cliente.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.Cliente.endereco.cep: CEP inválido")
                        )
                );
            });

            assertNull(cliente.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizaClienteInvalido(){
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpf like '484.854.847-03'", Cliente.class);
        Cliente cliente = query.getSingleResult();
        cliente.setEmail("email_invalido"); // Email inválido

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("deve ser um endereço de e-mail bem formado", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
    
}
