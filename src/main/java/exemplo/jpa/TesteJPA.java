package exemplo.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class TesteJPA {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("projeto-semestre");

    static {
        Logger.getGlobal().setLevel(Level.INFO);
    }

    public static void main(String[] args) throws IOException {
        try {
            Long id = inserirUsuario();
            consultarUsuario(id);
            //excluirUsuario(id);
        } finally {
            emf.close();
        }
    }

    private static void consultarUsuario(Long id) {
        EntityManager em = null;
        try {            
            em = emf.createEntityManager();
            Logger.getGlobal().log(Level.INFO, "Consultando usuário na base");
            Usuario usuario = em.find(Usuario.class, id);
            Logger.getGlobal().log(Level.INFO, "Imprimindo usuário (telefones serão recuperados agora)");
            System.out.println(usuario);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    private static void excluirUsuario(Long id){

        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            Usuario usuario = em.find(Usuario.class, id);
            em.remove(usuario);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                Logger.getGlobal().log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                Logger.getGlobal().info("Transação cancelada.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static Long inserirUsuario() throws IOException {
//        Usuario usuario = criarUsuario();
//        CartaoCredito cartaoCredito = criarCartaoCredito();
//        usuario.setCartaoCredito(cartaoCredito);
        
        Fornecedor fornecedor = criarFornecedor();
        Cliente cliente = criarCliente();
        
        
        
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(fornecedor);
            em.persist(cliente);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                Logger.getGlobal().log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                Logger.getGlobal().info("Transação cancelada.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return cliente.getId();
    }

    private static Fornecedor criarFornecedor(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fulano da silva");
        fornecedor.setEmail("fulano@gmail.com");
        fornecedor.setLogin("fulano");
        fornecedor.setSenha("teste");
        fornecedor.setCpf("534.585.764-45");
        fornecedor.addTelefone("888884325");
        
        Servico servico = new Servico();
        servico.setNome("Passagem");
        servico.setTipo("Físico");
        servico.setPreco(301.20);
    
        fornecedor.addServico(servico);
        servico.addFornecedor(fornecedor);
        criarEndereco(fornecedor);
        return fornecedor;
        
    }
    private static Cliente criarCliente() throws FileNotFoundException, IOException{
        Cliente cliente = new Cliente();
        cliente.setNome("Fulano da silva sauro");
        cliente.setEmail("fulano-sauro@gmail.com");
        cliente.setLogin("fulano-sauro");
        cliente.setSenha("teste-1");
        cliente.setCpf("539.585.764-45");
        cliente.addTelefone("888884325");
        
        // Cria uma imagem de exemplo
        Imagem img = new Imagem();
        FileInputStream fin = new FileInputStream("/home/rafael/Downloads/Teste.png");
        
        img.setImagem(fin.readAllBytes()); // Simula conteúdo binário
        cliente.addImage(img);
        img.setCliente(cliente);
        cliente.getImages().add(img);
        
        criarEndereco(cliente);
        
        Pedido pedido=criarPedido();
        cliente.setPedido(pedido);
        return cliente;
    }
            
    public static Pedido criarPedido(){
        Pedido pedido=new Pedido();
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        
       
        pedido.setData(c.getTime());
        pedido.setTipo(TipoPagamento.PIX);
       
        return pedido;
        
    }
    public static void criarEndereco(Usuario usuario) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setCep("50690-220");
        endereco.setNumero(550);
        usuario.setEndereco(endereco);
    }
}
